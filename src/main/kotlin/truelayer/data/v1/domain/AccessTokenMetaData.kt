package truelayer.data.v1.domain

import com.fasterxml.jackson.annotation.JsonProperty


data class AccessTokenMetaData(
        @JsonProperty("results")
        val results: List<Result>
)

data class Result(
        @JsonProperty("client_id")
        val clientId: String,
        @JsonProperty("consent_created_at")
        val consentCreatedAt: String,
        @JsonProperty("consent_expires_at")
        val consentExpiresAt: String,
        @JsonProperty("consent_status")
        val consentStatus: String,
        @JsonProperty("consent_status_updated_at")
        val consentStatusUpdatedAt: String,
        @JsonProperty("credentials_id")
        val credentialsId: String,
        @JsonProperty("privacy_policy")
        val privacyPolicy: String,
        @JsonProperty("provider")
        val provider: Provider,
        @JsonProperty("scopes")
        val scopes: List<String>
)

data class Provider(
        @JsonProperty("display_name")
        val displayName: String,
        @JsonProperty("logo_uri")
        val logoUri: String,
        @JsonProperty("provider_id")
        val providerId: String
)