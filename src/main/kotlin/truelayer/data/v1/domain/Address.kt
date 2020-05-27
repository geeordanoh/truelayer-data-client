package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Address(
    @JsonProperty("address")
    val address: String,
    @JsonProperty("city")
    val city: String,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("zip")
    val zip: String
)