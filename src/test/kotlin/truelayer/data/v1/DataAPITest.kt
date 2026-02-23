package truelayer.data.v1

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.*
import kotlin.test.assertEquals

class DataAPITest {

    private lateinit var dataAPI: DataAPI
    private lateinit var v1APIClient: V1APIClient
    private var accessToken = AccessToken("", "", "", "")

    @Before
    fun setUp() {
        v1APIClient = mockk()
        dataAPI = DataAPI(v1APIClient, accessToken)
    }

    @Test
    fun `get metadata`() {
        val accessTokenMetaData = mockk<AccessTokenMetaData>()

        every { v1APIClient.getMetaDataFor(accessToken) } returns accessTokenMetaData

        val result = dataAPI.getMetaDataFor()

        verify(exactly = 1) { v1APIClient.getMetaDataFor(accessToken) }
        assertEquals(result, accessTokenMetaData)
    }

    @Test
    fun `get identity info`() {
        val identityInfoResult = mockk<IdentityInfoResult>()
        val identityInfo = mockk<IdentityInfo>()

        every { identityInfoResult.identityInfoList[0] } returns identityInfo
        every { v1APIClient.getIdentityInfo(accessToken) } returns identityInfoResult

        val result = dataAPI.getIdentityInfo()

        verify(exactly = 1) { v1APIClient.getIdentityInfo(accessToken) }
        assertEquals(result, identityInfo)
    }

    @Test
    fun `get account list`() {
        val accounts = mockk<Accounts>()
        val accountList = mockk<List<Account>>()

        every { v1APIClient.getAccounts(accessToken) } returns accounts
        every { accounts.accountsList } returns accountList

        val result = dataAPI.getAccounts()

        verify(exactly = 1) { v1APIClient.getAccounts(accessToken) }
        assertEquals(result, accountList)
    }

    @Test
    fun `get account`() {
        val accounts = mockk<Accounts>()
        val account = mockk<Account>()
        val id = "fakeid"

        every { v1APIClient.getAccount(accessToken, id) } returns accounts
        every { accounts.accountsList[0] } returns account

        val result = dataAPI.getAccount(id)

        verify(exactly = 1) { v1APIClient.getAccount(accessToken, id) }
        assertEquals(result, account)
    }

    @Test
    fun `get transactions without date filter`() {
        val transactionsResult = mockk<TransactionsResult>()
        val transactionList = mockk<List<Transaction>>()
        val id = "fakeid"

        every { v1APIClient.getTransactions(accessToken, id, null, null) } returns transactionsResult
        every { transactionsResult.transactionList } returns transactionList

        val result = dataAPI.getTransactions(id)

        verify(exactly = 1) { v1APIClient.getTransactions(accessToken, id, null, null) }
        assertEquals(result, transactionList)
    }

    @Test
    fun `get transactions with from and to date filter`() {
        val transactionsResult = mockk<TransactionsResult>()
        val transactionList = mockk<List<Transaction>>()
        val id = "fakeid"
        val from = "2019-04-01T00:00:00Z"
        val to = "2019-04-30T23:59:59Z"

        every { v1APIClient.getTransactions(accessToken, id, from, to) } returns transactionsResult
        every { transactionsResult.transactionList } returns transactionList

        val result = dataAPI.getTransactions(id, from, to)

        verify(exactly = 1) { v1APIClient.getTransactions(accessToken, id, from, to) }
        assertEquals(result, transactionList)
    }
}