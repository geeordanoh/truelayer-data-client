package truelayer.data.auth.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class DebugCredentials(
        @JsonProperty("credentials_id")
        val credentialsId: String,
        @JsonProperty("debug_id")
        val debugId: String,
        @JsonProperty("provider_id")
        val providerId: String
)