package truelayer.data.auth.domain

import com.fasterxml.jackson.annotation.JsonProperty

class Providers : ArrayList<ProvidersItem>()

data class ProvidersItem(
        @JsonProperty("country")
        val country: String,
        @JsonProperty("display_name")
        val displayName: String,
        @JsonProperty("logo_url")
        val logoUrl: String,
        @JsonProperty("provider_id")
        val providerId: String,
        @JsonProperty("scopes")
        val scopes: List<String>
)