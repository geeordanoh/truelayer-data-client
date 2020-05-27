package truelayer.data.rest.auth

import truelayer.data.rest.RestClientConfiguration

class AuthRestClientFactory {

    companion object DefaultClient {
        fun create(restClientConfiguration: RestClientConfiguration): AuthAPIClient {
            return FuelRestClient(restClientConfiguration)
        }
    }
}