package truelayer.data.v1

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import truelayer.data.rest.auth.AuthAPIClient
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.*
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.DataAPI
import truelayer.data.v1.domain.AccessTokenMetaData
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

    private fun buildSomeAccessToken() = AccessToken("", "", "", "", "")
}