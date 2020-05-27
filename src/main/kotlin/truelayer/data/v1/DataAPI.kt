package truelayer.data.v1

import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.AccessTokenMetaData
import truelayer.data.v1.domain.IdentityInfo

class DataAPI(
        private val V1APIClient: V1APIClient) {

    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData {
        return V1APIClient.getMetaDataFor(accessToken)
    }

    fun getIdentityInfo(accessToken: AccessToken): IdentityInfo {
        return V1APIClient.getIdentityInfo(accessToken)
    }
}