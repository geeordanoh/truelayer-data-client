package truelayer.data.auth.rest

import main.kotlin.truelayer.client.data.rest.RestClient

class RestClientFactory {

    companion object DefaultClient {
        fun create(restClientConfiguration: RestClientConfiguration): RestClient {
            return FuelRestClient(restClientConfiguration)
        }
    }
}