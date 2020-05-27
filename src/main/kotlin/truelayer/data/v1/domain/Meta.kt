package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Meta(
    @JsonProperty("bank_transaction_id")
    val bankTransactionId: String,
    @JsonProperty("provider_transaction_category")
    val providerTransactionCategory: String
)