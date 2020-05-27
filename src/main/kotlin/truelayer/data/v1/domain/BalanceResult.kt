package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class BalanceResult(
    @JsonProperty("results")
    val balanceList: List<Balance>
)