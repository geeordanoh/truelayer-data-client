package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Accounts(
    @JsonProperty("results")
    val accounts: List<Account>
)