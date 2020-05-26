package truelayer.data.auth

import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.*

class Authenticator(
        private val clientCredentials: ClientCredentials,
        private val restClient: RestClient) {

    fun retrieveToken(tokenRequestParameters: TokenRequestParameters): AccessToken {
        return restClient.retrieveToken(clientCredentials, tokenRequestParameters)
    }

    fun refreshToken(tokenRefreshParameters: TokenRefreshParameters): AccessToken {
        return restClient.refreshToken(clientCredentials, tokenRefreshParameters)
    }

    fun deleteToken(accessToken: AccessToken) {
        return restClient.deleteToken(accessToken)
    }

    fun submitForDebug(accessToken: AccessToken): DebugCredentials {
        return restClient.submitForDebug(accessToken)
    }
}