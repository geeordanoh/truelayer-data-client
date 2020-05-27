package truelayer.data.rest.v1

import truelayer.data.auth.domain.*
import truelayer.data.v1.domain.AccessTokenMetaData
import truelayer.data.v1.domain.IdentityInfo

interface V1APIClient {
    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData
    fun getIdentityInfo(accessToken: AccessToken): IdentityInfo
}

