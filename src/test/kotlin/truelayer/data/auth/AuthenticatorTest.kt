package truelayer.data.auth

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import main.kotlin.truelayer.client.data.rest.RestClient
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials
import truelayer.data.auth.domain.TokenRefreshParameters
import truelayer.data.auth.domain.TokenRequestParameters

class AuthenticatorTest {

    private lateinit var restClient: RestClient
    private lateinit var authenticator: Authenticator
    private val credentials = buildSomeClientCredentials()

    @Before
    fun setUp() {
        restClient = mockk()
        authenticator = Authenticator(credentials, restClient)
    }

    @Test
    fun `retrieve token`() {
        val tokenRequestParameters = buildSomeTokenRequestParameters()
        every { restClient.retrieveToken(credentials, tokenRequestParameters) } returns buildSomeAccessToken()

        authenticator.retrieveToken(tokenRequestParameters)

        verify(exactly = 1) { restClient.retrieveToken(credentials, tokenRequestParameters) }
    }

    @Test
    fun `refresh token`() {
        val tokenRefreshParameters = buildSomeTokenRefreshParameters()
        every { restClient.refreshToken(credentials, tokenRefreshParameters) } returns buildSomeAccessToken()

        authenticator.refreshToken(tokenRefreshParameters)

        verify(exactly = 1) { restClient.refreshToken(credentials, tokenRefreshParameters) }
    }

    @Test
    fun `delete token`() {
        val accessToken = buildSomeAccessToken()
        val restClient = mockk<RestClient>(relaxed = true)
        authenticator = Authenticator(credentials, restClient)

        authenticator.deleteToken(accessToken)

        verify(exactly = 1) { restClient.deleteToken(accessToken) }
    }

    @Test
    fun `submit for debug`() {
        val accessToken = buildSomeAccessToken()
        val restClient = mockk<RestClient>(relaxed = true)
        authenticator = Authenticator(credentials, restClient)

        authenticator.submitForDebug(accessToken)

        verify(exactly = 1) { restClient.submitForDebug(accessToken) }
    }

    private fun buildSomeClientCredentials() = ClientCredentials("", "")
    private fun buildSomeTokenRefreshParameters() = TokenRefreshParameters("")
    private fun buildSomeTokenRequestParameters() = TokenRequestParameters("", "", "")
    private fun buildSomeAccessToken() = AccessToken("", "", "", "", "")
}