package truelayer.data.auth

import truelayer.data.auth.domain.*
import truelayer.data.rest.auth.AuthAPIClient

class Authenticator(
        private val clientCredentials: ClientCredentials,
        private val AuthAPIClient: AuthAPIClient) {

    fun retrieveToken(tokenRequestParameters: TokenRequestParameters): AccessToken {
        return AuthAPIClient.retrieveToken(clientCredentials, tokenRequestParameters)
    }

    fun refreshToken(tokenRefreshParameters: TokenRefreshParameters): AccessToken {
        return AuthAPIClient.refreshToken(clientCredentials, tokenRefreshParameters)
    }

    fun deleteToken(accessToken: AccessToken) {
        return AuthAPIClient.delete(accessToken)
    }

    fun submitForDebug(accessToken: AccessToken): DebugCredentials {
        return AuthAPIClient.submitForDebug(accessToken)
    }

    fun getProviders(): Providers {
        return AuthAPIClient.getProviders()
    }
}