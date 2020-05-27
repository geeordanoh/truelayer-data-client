package truelayer.data.rest.v1

import truelayer.data.auth.domain.*
import truelayer.data.v1.domain.AccessTokenMetaData

interface V1APIClient {
    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData
}

