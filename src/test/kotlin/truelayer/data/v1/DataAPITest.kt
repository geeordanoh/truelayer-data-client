package truelayer.data.v1

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.*
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.AccessTokenMetaData
import truelayer.data.v1.domain.Accounts
import truelayer.data.v1.domain.IdentityInfo
import kotlin.test.assertEquals

class DataAPITest {

    private lateinit var dataAPI: DataAPI
    private lateinit var v1APIClient: V1APIClient

    @Before
    fun setUp() {
        v1APIClient = mockk()
        dataAPI = DataAPI(v1APIClient)
    }

    @Test
    fun `get metadata`() {
        val accessToken = buildSomeAccessToken()
        val accessTokenMetaData = mockk<AccessTokenMetaData>()

        every { v1APIClient.getMetaDataFor(accessToken) } returns accessTokenMetaData

        val result = dataAPI.getMetaDataFor(accessToken)

        verify(exactly = 1) { v1APIClient.getMetaDataFor(accessToken) }
        assertEquals(result, accessTokenMetaData)
    }

    @Test
    fun `get identity info`() {
        val accessToken = buildSomeAccessToken()
        val identityInfo = mockk<IdentityInfo>()

        every { v1APIClient.getIdentityInfo(accessToken) } returns identityInfo

        val result = dataAPI.getIdentityInfo(accessToken)

        verify(exactly = 1) { v1APIClient.getIdentityInfo(accessToken) }
        assertEquals(result, identityInfo)
    }

    @Test
    fun `get accounts`() {
        val accessToken = buildSomeAccessToken()
        val accounts = mockk<Accounts>()

        every { v1APIClient.getAccounts(accessToken) } returns accounts

        val result = dataAPI.getAccounts(accessToken)

        verify(exactly = 1) { v1APIClient.getAccounts(accessToken) }
        assertEquals(result, accounts)
    }

    private fun buildSomeAccessToken() = AccessToken("", "", "", "", "")
}