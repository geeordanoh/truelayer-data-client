package truelayer.data.auth.rest

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.jackson.responseObject
import main.kotlin.truelayer.client.data.rest.RestClient
import truelayer.data.auth.domain.*

private const val BASE_URL = "https://auth.truelayer.com/"
private const val BASE_SANDBOX_URL = "https://auth.truelayer-sandbox.com/"
private const val RETRIEVE_TOKEN_URL = "connect/token"
private const val DELETE_TOKEN_URL = "api/delete"


class FuelRestClient(restClientConfiguration: RestClientConfiguration) : RestClient {


    init {
        if (restClientConfiguration.useSandbox)
            FuelManager.instance.basePath = BASE_SANDBOX_URL
        else
            FuelManager.instance.basePath = BASE_URL

        FuelManager.instance.timeoutReadInMillisecond = restClientConfiguration.timeoutReadInMillisecond
        FuelManager.instance.timeoutInMillisecond = restClientConfiguration.timeoutInMillisecond
    }

    override fun retrieveToken(credentials: ClientCredentials, tokenRequestParameters: TokenRequestParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.AUTHORIZATION_CODE.value,
                "redirect_uri" to tokenRequestParameters.redirectUri,
                "code" to tokenRequestParameters.code
        )
        return issuePostRequestWithUrlEncoding(request)
    }

    override fun refreshToken(credentials: ClientCredentials, tokenRefreshParameters: TokenRefreshParameters): AccessToken {
        val request = listOf(
                "client_id" to credentials.clientId,
                "client_secret" to credentials.clientSecret,
                "grant_type" to GrantType.REFRESH_TOKEN.value,
                "refresh_token" to tokenRefreshParameters.refreshToken
        )
        return issuePostRequestWithUrlEncoding(request)
    }

    override fun deleteToken(credentials: ClientCredentials, accessToken: AccessToken) {
        issueDeleteRequest(accessToken.accessTokenJWT)
    }

    private fun issueDeleteRequest(token: String) {
        val (_, response, _) = FuelManager.instance.delete(DELETE_TOKEN_URL).header(Pair("Authorization", "Bearer $token")).response()
        if (response.statusCode != 200) throw InvalidRequestException("Request failed with status code ${response.statusCode}")
    }

    private fun issuePostRequestWithUrlEncoding(request: List<Pair<String, String>>): AccessToken {
        val (_, response, result) = FuelManager.instance.post(RETRIEVE_TOKEN_URL, request).responseObject<AccessToken>()
        return result.fold<AccessToken>({ return it }, { throw InvalidRequestException("Request failed with status code ${response.statusCode}", it) })
    }
}