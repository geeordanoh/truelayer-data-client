package truelayer.data.auth.rest

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.responseObject
import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials

private const val URL = "https://auth.truelayer.com/connect/token"
private const val URL_SANDBOX = "https://auth.truelayer-sandbox.com/connect/token"

class FuelRestClient(private val useSandbox : Boolean) : RestClient{

    override fun retrieveToken(credentials: ClientCredentials): AccessToken {
        val (_, _, result) = Fuel.post(getUrl(useSandbox), adaptCredentials(credentials))
                .responseObject<AccessToken>()
        result.fold({ accessToken ->
            return accessToken
        }, { err ->
            throw err
        })
    }

    override fun refreshToken(credentials: ClientCredentials): AccessToken {
        throw NotImplementedError()
    }

    private fun adaptCredentials(credentials: ClientCredentials): List<Pair<String, String>> {
        return listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to credentials.grantType.value,
                "redirect_uri" to credentials.redirectUri,
                "code" to credentials.code
        )
    }

    private fun getUrl(useSandbox: Boolean): String = if (useSandbox) URL_SANDBOX else URL
}