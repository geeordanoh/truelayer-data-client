package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Account(
    @JsonProperty("account_id")
    val accountId: String,
    @JsonProperty("account_number")
    val accountNumber: AccountNumber,
    @JsonProperty("account_type")
    val accountType: String,
    @JsonProperty("currency")
    val currency: String,
    @JsonProperty("display_name")
    val displayName: String,
    @JsonProperty("provider")
    val provider: Provider,
    @JsonProperty("update_timestamp")
    val updateTimestamp: String
)