package truelayer.data.v1

import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.*

class DataAPI(
        private val V1APIClient: V1APIClient,
        private val accessToken: AccessToken) {

    fun getMetaDataFor(): AccessTokenMetaData {
        return V1APIClient.getMetaDataFor(accessToken)
    }

    fun getIdentityInfo(): IdentityInfo {
        return V1APIClient.getIdentityInfo(accessToken).identityInfoList[0]
    }

    fun getAccounts(): List<Account> {
        return V1APIClient.getAccounts(accessToken).accountsList
    }

    fun getAccount(accountId: String): Account {
        return V1APIClient.getAccount(accessToken, accountId).accountsList[0]
    }

    fun getBalance(accountId: String): Balance {
        return V1APIClient.getBalance(accessToken, accountId).balanceList[0]
    }

    fun getTransactions(accountId: String): List<Transaction> {
        return V1APIClient.getTransactions(accessToken, accountId).transactionList
    }
}