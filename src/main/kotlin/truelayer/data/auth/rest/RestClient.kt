package main.kotlin.truelayer.client.data.rest

import truelayer.data.auth.domain.*
import truelayer.data.auth.domain.Providers

interface RestClient {

    fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken
    fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken
    fun deleteToken(accessToken: AccessToken)
    fun submitForDebug(accessToken: AccessToken): DebugCredentials
    fun getProviders() : Providers
}

