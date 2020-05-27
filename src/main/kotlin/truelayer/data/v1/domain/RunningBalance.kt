package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class RunningBalance(
    @JsonProperty("amount")
    val amount: Double,
    @JsonProperty("currency")
    val currency: String
)