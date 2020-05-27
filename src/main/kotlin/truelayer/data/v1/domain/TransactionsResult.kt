package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionsResult(
    @JsonProperty("results")
    val transactionList: List<Transaction>
)