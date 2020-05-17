package truelayer.data.auth

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import main.kotlin.truelayer.client.data.rest.RestClient
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.AccessToken
import truelayer.data.auth.domain.ClientCredentials
import truelayer.data.auth.domain.GrantType

class AuthenticatorTest {

    private lateinit var restClient: RestClient
    private lateinit var authenticator: Authenticator

    @Before
    fun setUp() {
        restClient = mockk()
        authenticator = Authenticator(restClient)
    }

    @Test
    fun `retrieveToken`() {
        val credentials = buildSomeAuthorizationCredentials()
        every { restClient.retrieveToken(credentials) } returns buildSomeAccessToken()

        authenticator.retrieveToken(credentials)

        verify(exactly = 1) { restClient.retrieveToken(credentials) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `wrongGrantType`() {
        val credentials = buildSomeRefreshCredentials()
        every { restClient.retrieveToken(credentials) } returns buildSomeAccessToken()

        authenticator.retrieveToken(credentials)
    }

    private fun buildSomeAuthorizationCredentials() = ClientCredentials(GrantType.AUTHORIZATION_CODE, "", "", "", "", "")
    private fun buildSomeRefreshCredentials() = ClientCredentials(GrantType.REFRESH_TOKEN, "", "", "", "", "")
    private fun buildSomeAccessToken() = AccessToken("", "", "", "")
}