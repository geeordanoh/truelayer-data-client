package truelayer.data.v1.domain


import com.fasterxml.jackson.annotation.JsonProperty

data class IdentityInfoResult(
    @JsonProperty("results")
    val identityInfoList: List<IdentityInfo>
)