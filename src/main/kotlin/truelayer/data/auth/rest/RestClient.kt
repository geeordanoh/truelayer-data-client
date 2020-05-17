package main.kotlin.truelayer.client.data.rest

import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials

interface RestClient {
    fun retrieveToken(credentials: ClientCredentials): AccessToken
    fun refreshToken(credentials: ClientCredentials): AccessToken
}