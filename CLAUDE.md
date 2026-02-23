# CLAUDE.md

This file provides context for AI assistants working in this repository.

## Project Overview

`truelayer-data-client` is a Kotlin library for integrating with the [TrueLayer Data API](https://docs.truelayer.com/). It handles OAuth 2.0 authentication flows and exposes financial data endpoints (accounts, balances, transactions, identity). The project is currently a work in progress.

## Build System and Language

- **Language:** Kotlin 1.3.70 (JVM target: Java 8)
- **Build tool:** Maven (`pom.xml`)
- **No `package.json` or npm** — this is a JVM project, not a Node/JS project

## Common Maven Commands

```bash
mvn test                          # Run all unit tests
mvn compile                       # Compile Kotlin sources
mvn clean                         # Remove build artifacts (target/)
mvn clean test                    # Clean then run tests
mvn dependency:go-offline         # Pre-download all deps (used in CI caching)
mvn verify                        # Full build + tests
mvn install                       # Build and install to local ~/.m2
```

Maven requires the custom settings file for the Fuel library:

```bash
mvn -s settings.xml test          # Used in CI — includes Bintray repo for Fuel
```

## Repository Structure

```
truelayer-data-client/
├── src/
│   ├── main/kotlin/truelayer/data/
│   │   ├── auth/                      # Authentication facade + domain models
│   │   │   ├── Authenticator.kt       # High-level auth API (entry point for auth)
│   │   │   └── domain/                # AccessToken, ClientCredentials, GrantType, etc.
│   │   ├── rest/                      # HTTP client layer
│   │   │   ├── FuelRestClientCommons.kt   # Base class with GET/POST/DELETE helpers
│   │   │   ├── RestClientConfiguration.kt # Sandbox toggle + timeouts
│   │   │   ├── InvalidRequestException.kt # Custom API error exception
│   │   │   ├── auth/                  # Auth REST client (interface + Fuel impl + factory)
│   │   │   └── v1/                    # Data v1 REST client (interface + Fuel impl + factory)
│   │   └── v1/
│   │       ├── DataAPI.kt             # High-level data API facade (entry point for data)
│   │       └── domain/                # Account, Balance, Transaction, IdentityInfo, etc.
│   └── test/kotlin/truelayer/data/
│       ├── auth/AuthenticatorTest.kt  # Unit tests for Authenticator
│       └── v1/DataAPITest.kt          # Unit tests for DataAPI
├── .circleci/config.yml               # CircleCI build config
├── pom.xml                            # Maven build descriptor
├── settings.xml                       # Maven settings (custom Bintray repo for Fuel)
└── README.md                          # Project overview and backlog
```

## Architecture

The project uses a layered design with clear separation of concerns:

### Layer 1 — REST clients (`rest/`)
Low-level HTTP wrappers around the [Fuel](https://github.com/kittinunf/fuel) library.

- `FuelRestClientCommons` — base class providing `issueGetRequest<T>()`, `issuePostRequestWithUrlEncoding()`, `issueDeleteRequest()` with error handling
- `AuthFuelRestClient` / `V1FuelRestClient` — concrete implementations of their respective interfaces
- Factories (`AuthRestClientFactory`, `V1RestClientFactory`) — companion object pattern; single static `create(config)` method

### Layer 2 — High-level facades (`auth/`, `v1/`)
Simplified entry points that hide pagination, URL selection, and response unwrapping.

- `Authenticator` — wraps `AuthAPIClient`; handles token exchange, refresh, deletion, debug, providers
- `DataAPI` — wraps `V1APIClient`; unwraps `results` lists and returns first item where appropriate

### Domain models (`auth/domain/`, `v1/domain/`)
Kotlin `data class` objects. All use Jackson `@JsonProperty` annotations for JSON deserialization. No business logic lives in domain classes.

## Key Conventions

### Naming
| Element | Convention | Example |
|---|---|---|
| Classes/Interfaces | PascalCase | `AuthFuelRestClient` |
| Functions | camelCase | `retrieveToken()` |
| URL constants | UPPER_SNAKE_CASE | `BASE_URL`, `RETRIEVE_TOKEN_URL` |
| Packages | lowercase dot-separated | `truelayer.data.auth.domain` |

### Patterns in use
- **Factory pattern** — `AuthRestClientFactory.create(config)` and `V1RestClientFactory.create(config)` via companion objects
- **Interface/implementation split** — `AuthAPIClient` (interface) + `AuthFuelRestClient` (impl); `V1APIClient` + `V1FuelRestClient`
- **Facade pattern** — `Authenticator` and `DataAPI` provide clean high-level APIs over the REST clients
- **Reified generics** — `inline fun <reified T : Any> issueGetRequest(...)` for type-safe JSON deserialization

### Kotlin idioms
- Prefer `data class` for domain models (immutability, `copy()`, `equals()`/`hashCode()`)
- Optional parameters use default values (`codeVerifier: String? = null`)
- Backtick-quoted function names in tests for human-readable test descriptions
- Fold over Fuel `Result<T, FuelError>` — `.fold({ /* success */ }, { /* error */ })`

## Testing

- **Framework:** JUnit 4.12 with `kotlin-test` assertions
- **Mocking:** MockK 1.10.0 (`mockk<T>()`, relaxed mocks where needed)
- **Style:** `@Before` setup, `@Test` methods, backtick names for readability
- **Scope:** Unit tests only (integration tests are in the backlog)

Example test structure:
```kotlin
@Before
fun setUp() {
    mockAuthAPIClient = mockk()
    authenticator = Authenticator(mockAuthAPIClient)
}

@Test
fun `retrieve token delegates to auth api client`() {
    every { mockAuthAPIClient.retrieveToken(any()) } returns someToken
    val result = authenticator.retrieveToken(someParams)
    verify(exactly = 1) { mockAuthAPIClient.retrieveToken(someParams) }
    assertEquals(someToken, result)
}
```

## API Endpoints Reference

### Auth API (`AuthFuelRestClient`)
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/connect/token` | Exchange auth code for access token |
| POST | `/connect/token` | Refresh access token |
| POST | `/api/delete` | Invalidate/delete access token |
| POST | `/api/debug` | Submit token for debug inspection |
| GET  | `/api/providers` | List all supported bank providers |

Base URLs:
- Production: `https://auth.truelayer.com/`
- Sandbox: `https://auth-truelayer-sandbox.com/`

### Data v1 API (`V1FuelRestClient`)
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/data/v1/me` | Access token metadata |
| GET | `/data/v1/info` | User identity information |
| GET | `/data/v1/accounts` | List all accounts |
| GET | `/data/v1/accounts/{id}` | Single account details |
| GET | `/data/v1/accounts/{id}/balance` | Account balance |
| GET | `/data/v1/accounts/{id}/transactions` | Account transactions |

Base URLs:
- Production: `https://truelayer.com/`
- Sandbox: `https://truelayer-sandbox.com/`

## Configuration

`RestClientConfiguration` controls runtime behaviour:

```kotlin
data class RestClientConfiguration(
    val useSandbox: Boolean = false,
    val timeoutInMillisecond: Int = 10000,
    val timeoutReadInMillisecond: Int = 10000
)
```

Pass this to the factory:
```kotlin
val config = RestClientConfiguration(useSandbox = true)
val authClient = AuthRestClientFactory.create(config)
val dataClient = V1RestClientFactory.create(config)
```

## Dependencies

| Library | Version | Role |
|---|---|---|
| `kotlin-stdlib-jdk8` | 1.3.70 | Kotlin standard library |
| `fuel` | 2.2.2 | HTTP client |
| `fuel-jackson` | 2.2.2 | JSON deserialization via Jackson |
| `fuel-rxjava` | 2.2.2 | RxJava integration |
| `fuel-coroutines` | 2.2.2 | Kotlin coroutines support |
| `jackson-databind` | 2.9.10.4 | JSON processing |
| `junit` | 4.12 | Test framework |
| `kotlin-test` | 1.3.70 | Kotlin test assertions |
| `mockk` | 1.10.0 | Kotlin mocking |

> **Note:** The Fuel library is hosted on Bintray (`https://dl.bintray.com/kittinunf/maven`). The `settings.xml` file must be passed to Maven commands (`mvn -s settings.xml`) when running outside of the project's preconfigured CI environment.

## CI/CD

CircleCI (`.circleci/config.yml`):
1. Checks out source
2. Restores Maven cache (keyed by `pom.xml` checksum)
3. Downloads dependencies offline (`mvn -s settings.xml dependency:go-offline`)
4. Saves cache to `~/.m2`
5. Runs tests (`mvn -s settings.xml test`)

Docker image: `circleci/openjdk:8-jdk-stretch`

## What's Implemented vs Planned

Refer to `README.md` for the full backlog. Summary:

**Done:** Token exchange, refresh, delete, providers list, CI setup

**In progress / drafted:** Debug submission, metadata, identity, accounts, balance, transactions

**Planned:** Pending transactions, direct debits, standing orders, card endpoints, combined transactions, income/expenditure, integration tests, JitPack releases

## Tips for AI Assistants

- Adding a new data endpoint follows the pattern: add method to `V1APIClient` interface → implement in `V1FuelRestClient` → expose via `DataAPI` facade → add corresponding `data class` in `v1/domain/` with `@JsonProperty` annotations → add unit test in `DataAPITest.kt`
- Adding a new auth operation follows: add to `AuthAPIClient` interface → implement in `AuthFuelRestClient` → expose via `Authenticator` → add unit test in `AuthenticatorTest.kt`
- Always run `mvn -s settings.xml test` to verify changes; tests must pass before committing
- Do not add new dependencies without checking compatibility with Kotlin 1.3.70 / JVM 8
- Sandbox vs production is controlled solely by `RestClientConfiguration.useSandbox`; do not hardcode URLs elsewhere
