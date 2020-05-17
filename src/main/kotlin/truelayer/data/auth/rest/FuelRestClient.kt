package truelayer.data.auth.rest

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.objectBody
import com.github.kittinunf.fuel.jackson.responseObject
import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials

private const val URL = "https://auth.truelayer.com/connect/token"

class FuelRestClient : RestClient {

    override fun retrieveToken(credentials: ClientCredentials): AccessToken {
        val (_, _, err) = Fuel.post(URL)
                .objectBody(credentials, Charsets.UTF_16)
                .responseObject<AccessToken>()
        err.fold({ accessToken ->
            return accessToken
        }, { err ->
            throw err
        })
    }

    override fun refreshToken(credentials: ClientCredentials): AccessToken {
        throw NotImplementedError()
    }
}