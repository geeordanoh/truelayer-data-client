package truelayer.data.auth.rest

import main.kotlin.truelayer.client.data.rest.RestClient

class RestClientFactory {

    companion object DefaultClient {
        fun create(useSandbox : Boolean = false): RestClient{
            return FuelRestClient(useSandbox)
        }
    }
}