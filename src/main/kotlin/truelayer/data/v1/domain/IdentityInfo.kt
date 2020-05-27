package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class IdentityInfo(
    @JsonProperty("results")
    val infoResults: List<InfoResult>
)