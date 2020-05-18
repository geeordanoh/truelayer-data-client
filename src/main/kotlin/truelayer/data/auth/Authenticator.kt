package truelayer.data.auth

import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials
import truelayer.data.auth.domain.GrantType
import truelayer.data.auth.rest.RestClientFactory

class Authenticator(private val restClient: RestClient = RestClientFactory.DefaultClient) {

    fun retrieveToken(credentials: ClientCredentials): AccessToken {
        return if( credentials.grantType.equals(GrantType.AUTHORIZATION_CODE)) restClient.retrieveToken(credentials) else throw IllegalArgumentException()
    }
}