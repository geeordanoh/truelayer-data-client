package truelayer.data.rest.auth

import truelayer.data.auth.domain.*

interface AuthAPIClient {
    fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken
    fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken
    fun delete(accessToken: AccessToken)
    fun submitForDebug(accessToken: AccessToken): DebugCredentials
    fun getProviders(): Providers
}

