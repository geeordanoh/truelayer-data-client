package truelayer.data.rest.auth

import com.github.kittinunf.fuel.core.FuelManager
import truelayer.data.auth.domain.*
import truelayer.data.rest.FuelRestClientCommons
import truelayer.data.rest.RestClientConfiguration


private const val BASE_URL = "https://auth.truelayer.com/"
private const val BASE_SANDBOX_URL = "https://auth.truelayer-sandbox.com/"
private const val RETRIEVE_TOKEN_URL = "connect/token"
private const val DELETE_TOKEN_URL = "api/delete"
private const val DEBUG_URL = "api/debug"
private const val PROVIDERS_URL = "api/providers"

class FuelRestClient(restClientConfiguration: RestClientConfiguration) : AuthAPIClient, FuelRestClientCommons() {

    private var fuelManager: FuelManager = FuelManager()

    init {
        if (restClientConfiguration.useSandbox)
            fuelManager.basePath = BASE_SANDBOX_URL
        else
            fuelManager.basePath = BASE_URL

        fuelManager.timeoutReadInMillisecond = restClientConfiguration.timeoutReadInMillisecond
        fuelManager.timeoutInMillisecond = restClientConfiguration.timeoutInMillisecond
    }

    override fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.AUTHORIZATION_CODE.value,
                "redirect_uri" to tokenRequestParameters.redirectUri,
                "code" to tokenRequestParameters.code
        )
        return issuePostRequestWithUrlEncoding(fuelManager, RETRIEVE_TOKEN_URL, request)
    }

    override fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.REFRESH_TOKEN.value,
                "refresh_token" to tokenRefreshParameters.refreshToken
        )
        return issuePostRequestWithUrlEncoding(fuelManager, RETRIEVE_TOKEN_URL, request)
    }

    override fun delete(accessToken: AccessToken) {
        issueDeleteRequest(fuelManager, DELETE_TOKEN_URL, Pair("Authorization", "Bearer $accessToken.accessTokenJWT"))
    }

    override fun submitForDebug(accessToken: AccessToken): DebugCredentials {
        val request = listOf(
                "access_token" to accessToken.accessTokenJWT
        )
        return this.issuePostRequestWithUrlEncoding(fuelManager, DEBUG_URL, request, Pair("Content-Type", "application/json"))
    }

    override fun getProviders(): Providers {
        return issueGetRequest(fuelManager, PROVIDERS_URL)
    }
}