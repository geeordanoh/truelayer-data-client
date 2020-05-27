package truelayer.data.rest.v1

import truelayer.data.rest.RestClientConfiguration

class V1RestClientFactory {

    companion object DefaultClient {
        fun create(restClientConfiguration: RestClientConfiguration): V1APIClient {
            return V1FuelRestClient(restClientConfiguration)
        }
    }
}