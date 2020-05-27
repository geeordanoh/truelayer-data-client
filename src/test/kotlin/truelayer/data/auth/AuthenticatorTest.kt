package truelayer.data.auth

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.*
import truelayer.data.rest.auth.AuthAPIClient
import kotlin.test.assertEquals

class AuthenticatorTest {

    private lateinit var authAPIClient: AuthAPIClient
    private lateinit var authenticator: Authenticator
    private val credentials = buildSomeClientCredentials()

    @Before
    fun setUp() {
        authAPIClient = mockk()
        authenticator = Authenticator(credentials, authAPIClient)
    }

    @Test
    fun `retrieve token`() {
        val tokenRequestParameters = buildSomeTokenRequestParameters()
        val accessToken = buildSomeAccessToken()
        every { authAPIClient.retrieveToken(credentials, tokenRequestParameters) } returns accessToken

        val result = authenticator.retrieveToken(tokenRequestParameters)

        verify(exactly = 1) { authAPIClient.retrieveToken(credentials, tokenRequestParameters) }
        assertEquals(result, accessToken)
    }

    @Test
    fun `refresh token`() {
        val tokenRefreshParameters = buildSomeTokenRefreshParameters()
        val accessToken = buildSomeAccessToken()
        every { authAPIClient.refreshToken(credentials, tokenRefreshParameters) } returns accessToken

        val result = authenticator.refreshToken(tokenRefreshParameters)

        verify(exactly = 1) { authAPIClient.refreshToken(credentials, tokenRefreshParameters) }
        assertEquals(result, accessToken)
    }

    @Test
    fun `delete token`() {
        val accessToken = buildSomeAccessToken()
        val restClient = mockk<AuthAPIClient>(relaxed = true)
        authenticator = Authenticator(credentials, restClient)

        authenticator.deleteToken(accessToken)

        verify(exactly = 1) { restClient.delete(accessToken) }
    }

    @Test
    fun `submit for debug`() {
        val accessToken = buildSomeAccessToken()
        val debugCredentials = buildSomeDebugCredentials()
        every { authAPIClient.submitForDebug(accessToken) } returns debugCredentials

        val result = authenticator.submitForDebug(accessToken)

        verify(exactly = 1) { authAPIClient.submitForDebug(accessToken) }
        assertEquals(result, debugCredentials)
    }

    @Test
    fun `get providers list`() {
        val providers = buildSomeProviders()
        every { authAPIClient.getProviders() } returns providers

        val result = authenticator.getProviders()

        verify(exactly = 1) { authAPIClient.getProviders() }
        assertEquals(result, providers)
    }


    private fun buildSomeClientCredentials() = ClientCredentials("", "")
    private fun buildSomeTokenRefreshParameters() = TokenRefreshParameters("")
    private fun buildSomeTokenRequestParameters() = TokenRequestParameters("", "", "")
    private fun buildSomeAccessToken() = AccessToken("", "", "", "", "")
    private fun buildSomeDebugCredentials() = DebugCredentials("", "", "")
    private fun buildSomeProviders(): Providers{
        val providers = Providers()
        providers.add(ProvidersItem("","","","", listOf("")))
        return providers
    }
}