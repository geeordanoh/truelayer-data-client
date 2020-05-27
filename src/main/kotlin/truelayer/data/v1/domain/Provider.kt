package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class Provider(
    @JsonProperty("display_name")
    val displayName: String,
    @JsonProperty("logo_uri")
    val logoUri: String,
    @JsonProperty("provider_id")
    val providerId: String
)