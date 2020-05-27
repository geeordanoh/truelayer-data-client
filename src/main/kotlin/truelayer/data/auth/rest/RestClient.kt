package main.kotlin.truelayer.client.data.rest

import truelayer.data.auth.domain.*

interface RestClient {

    fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken
    fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken
    fun delete(accessToken: AccessToken)
    fun submitForDebug(accessToken: AccessToken): DebugCredentials
    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData
    fun getProviders(): Providers
}

