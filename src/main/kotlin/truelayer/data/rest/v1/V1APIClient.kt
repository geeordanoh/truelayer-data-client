package truelayer.data.rest.v1

import truelayer.data.auth.domain.AccessToken
import truelayer.data.v1.domain.*

interface V1APIClient {
    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData
    fun getIdentityInfo(accessToken: AccessToken): IdentityInfoResult
    fun getAccounts(accessToken: AccessToken): Accounts
    fun getAccount(accessToken: AccessToken, accountId: String): Accounts
    fun getBalance(accessToken: AccessToken, accountId: String): BalanceResult
    fun getTransactions(accessToken: AccessToken, accountId: String): TransactionsResult
}

