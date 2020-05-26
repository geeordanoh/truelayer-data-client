package truelayer.data.auth.rest

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.jackson.responseObject
import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.*


private const val BASE_URL = "https://auth.truelayer.com/"
private const val BASE_SANDBOX_URL = "https://auth.truelayer-sandbox.com/"
private const val RETRIEVE_TOKEN_URL = "connect/token"
private const val DELETE_TOKEN_URL = "api/delete"
private const val DEBUG_URL = "api/debug"
private const val PROVIDERS_URL = "api/providers"

class FuelRestClient(restClientConfiguration: RestClientConfiguration) : RestClient {

    private var fuelManager: FuelManager = FuelManager()

    init {
        if (restClientConfiguration.useSandbox)
            fuelManager.basePath = BASE_SANDBOX_URL
        else
            fuelManager.basePath = BASE_URL

        fuelManager.timeoutReadInMillisecond = restClientConfiguration.timeoutReadInMillisecond
        fuelManager.timeoutInMillisecond = restClientConfiguration.timeoutInMillisecond
    }

    override fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.AUTHORIZATION_CODE.value,
                "redirect_uri" to tokenRequestParameters.redirectUri,
                "code" to tokenRequestParameters.code
        )
        return issuePostRequestWithUrlEncoding(RETRIEVE_TOKEN_URL, request)
    }

    override fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.REFRESH_TOKEN.value,
                "refresh_token" to tokenRefreshParameters.refreshToken
        )
        return issuePostRequestWithUrlEncoding(RETRIEVE_TOKEN_URL, request)
    }

    override fun deleteToken(accessToken: AccessToken) {
        issueDeleteRequest(DELETE_TOKEN_URL, accessToken.accessTokenJWT)
    }

    override fun submitForDebug(accessToken: AccessToken): DebugCredentials {
        val request = listOf(
                "access_token" to accessToken.accessTokenJWT
        )
        return this.issuePostRequestWithUrlEncoding(DEBUG_URL, request, Pair("Content-Type", "application/json"))
    }

    override fun getProviders(): Providers {
        val (_, response, result) = fuelManager.get(PROVIDERS_URL).responseObject<Providers>()
        return result.fold<Providers>({ return it }, { throw InvalidRequestException("Request failed with status code ${response.statusCode}", it) })
    }

    private fun issueDeleteRequest(url: String, token: String) {
        val (_, response, _) = fuelManager
                .delete(url)
                .header(Pair("Authorization", "Bearer $token"))
                .response()
        if (response.statusCode != 200) throw InvalidRequestException("Request failed with status code ${response.statusCode}")
    }

    private inline fun <reified T : Any> issuePostRequestWithUrlEncoding(url: String, request: List<Pair<String, String>>, vararg headers: Pair<String, String>): T {
        val (_, response, result) = fuelManager
                .post(url, request)
                .header(*headers)
                .responseObject<T>()
        return result.fold<T>({ return it }, { throw InvalidRequestException("Request failed with status code ${response.statusCode}", it) })
    }
}