package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class AccountNumber(
    @JsonProperty("iban")
    val iban: String,
    @JsonProperty("number")
    val number: String,
    @JsonProperty("sort_code")
    val sortCode: String,
    @JsonProperty("swift_bic")
    val swiftBic: String
)