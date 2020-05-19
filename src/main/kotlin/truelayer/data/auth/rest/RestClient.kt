package main.kotlin.truelayer.client.data.rest

import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials
import truelayer.data.auth.domain.TokenRefreshParameters
import truelayer.data.auth.domain.TokenRequestParameters

interface RestClient {

    fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken
    fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken
    fun deleteToken(credentials: ClientCredentials, accessToken: AccessToken)
}

