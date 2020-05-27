package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Transaction(
    @JsonProperty("amount")
    val amount: Double,
    @JsonProperty("currency")
    val currency: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("merchant_name")
    val merchantName: String,
    @JsonProperty("meta")
    val meta: Meta,
    @JsonProperty("running_balance")
    val runningBalance: RunningBalance,
    @JsonProperty("timestamp")
    val timestamp: String,
    @JsonProperty("transaction_category")
    val transactionCategory: String,
    @JsonProperty("transaction_classification")
    val transactionClassification: List<String>,
    @JsonProperty("transaction_id")
    val transactionId: String,
    @JsonProperty("transaction_type")
    val transactionType: String
)