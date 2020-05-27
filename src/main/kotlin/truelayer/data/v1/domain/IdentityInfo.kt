package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class IdentityInfo(
        @JsonProperty("addresses")
    val addresses: List<Address>,
        @JsonProperty("date_of_birth")
    val dateOfBirth: String,
        @JsonProperty("emails")
    val emails: List<String>,
        @JsonProperty("full_name")
    val fullName: String,
        @JsonProperty("phones")
    val phones: List<String>,
        @JsonProperty("update_timestamp")
    val updateTimestamp: String
)