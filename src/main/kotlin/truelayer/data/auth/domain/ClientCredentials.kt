package truelayer.data.auth.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ClientCredentials(
        val grantType: GrantType,
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String,
        val code: String,
        val codeVerifier: String?
)