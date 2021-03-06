package truelayer.data.rest.v1

import com.github.kittinunf.fuel.core.FuelManager
import truelayer.data.auth.domain.AccessToken
import truelayer.data.rest.FuelRestClientCommons
import truelayer.data.rest.RestClientConfiguration
import truelayer.data.v1.domain.*

private const val BASE_URL = "https://truelayer.com/"
private const val BASE_SANDBOX_URL = "https://truelayer-sandbox.com/"
private const val METADATA_URL = "data/v1/me"
private const val INFO_URL = "data/v1/info"
private const val ACCOUNTS_URL = "data/v1/accounts"

class V1FuelRestClient(restClientConfiguration: RestClientConfiguration) : V1APIClient, FuelRestClientCommons() {

    private var fuelManager: FuelManager = FuelManager()

    init {
        if (restClientConfiguration.useSandbox)
            fuelManager.basePath = BASE_SANDBOX_URL
        else
            fuelManager.basePath = BASE_URL

        fuelManager.timeoutReadInMillisecond = restClientConfiguration.timeoutReadInMillisecond
        fuelManager.timeoutInMillisecond = restClientConfiguration.timeoutInMillisecond
    }

    override fun getMetaDataFor(accessToken: AccessToken): AccessTokenMetaData {
        return issueGetRequest(fuelManager, METADATA_URL, Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }

    override fun getIdentityInfo(accessToken: AccessToken): IdentityInfoResult {
        return issueGetRequest(fuelManager, INFO_URL, Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }

    override fun getAccounts(accessToken: AccessToken): Accounts {
        return issueGetRequest(fuelManager, ACCOUNTS_URL, Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }

    override fun getAccount(accessToken: AccessToken, accountId: String): Accounts {
        return issueGetRequest(fuelManager, "$ACCOUNTS_URL/$accountId", Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }

    override fun getBalance(accessToken: AccessToken, accountId: String): BalanceResult {
        return issueGetRequest(fuelManager, "$ACCOUNTS_URL/$accountId/balance", Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }

    override fun getTransactions(accessToken: AccessToken, accountId: String): TransactionsResult {
        //TODO(add from to parameters)
        return issueGetRequest(fuelManager, "$ACCOUNTS_URL/$accountId/transactions", Pair("Authorization", "Bearer ${accessToken.accessTokenJWT}"))
    }
}