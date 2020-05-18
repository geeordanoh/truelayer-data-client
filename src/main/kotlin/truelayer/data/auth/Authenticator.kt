package truelayer.data.auth

import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials
import truelayer.data.auth.domain.GrantType

class Authenticator(private val restClient: RestClient) {

    fun retrieveToken(credentials: ClientCredentials): AccessToken {
        return if (areValid(credentials)) restClient.retrieveToken(credentials) else throw IllegalArgumentException()
    }

    private fun areValid(credentials: ClientCredentials): Boolean = credentials.grantType.equals(GrantType.AUTHORIZATION_CODE)
}