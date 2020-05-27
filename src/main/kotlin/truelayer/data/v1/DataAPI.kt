package truelayer.data.v1

import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.AccessTokenMetaData

class DataAPI(
        private val V1APIClient: V1APIClient) {

    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData {
        return V1APIClient.getMetaDataFor(accessToken)
    }
}