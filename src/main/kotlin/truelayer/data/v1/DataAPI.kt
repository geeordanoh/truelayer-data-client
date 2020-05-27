package truelayer.data.v1

import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.v1.V1APIClient
import truelayer.data.v1.domain.*

class DataAPI(
        private val V1APIClient: V1APIClient) {

    fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData {
        return V1APIClient.getMetaDataFor(accessToken)
    }

    fun getIdentityInfo(accessToken: AccessToken): IdentityInfo {
        return V1APIClient.getIdentityInfo(accessToken).identityInfoList[0]
    }

    fun getAccounts(accessToken: AccessToken): List<Account> {
        return V1APIClient.getAccounts(accessToken).accountsList
    }

    fun getAccount(accessToken: AccessToken, accountId: String): Account {
        return V1APIClient.getAccount(accessToken, accountId).accountsList[0]
    }

    fun getBalance(accessToken: AccessToken, accountId: String): Balance{
        return V1APIClient.getBalance(accessToken, accountId).balanceList[0]
    }

    fun getTransactions(accessToken: AccessToken, accountId: String): List<Transaction>{
        return V1APIClient.getTransactions(accessToken, accountId).transactionList
    }
}