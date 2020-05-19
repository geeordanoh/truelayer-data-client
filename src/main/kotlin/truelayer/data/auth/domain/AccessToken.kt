package truelayer.data.auth.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AccessToken(
        @JsonProperty("access_token")
        val accessTokenJWT: String,
        @JsonProperty("expires_in")
        val expiresIn: String,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("refresh_token")
        val refreshToken: String,
        @JsonProperty("scope")
        val scope: String
)