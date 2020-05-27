package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Balance(
    @JsonProperty("available")
    val available: Double,
    @JsonProperty("currency")
    val currency: String,
    @JsonProperty("current")
    val current: Double,
    @JsonProperty("overdraft")
    val overdraft: Int,
    @JsonProperty("update_timestamp")
    val updateTimestamp: String
)