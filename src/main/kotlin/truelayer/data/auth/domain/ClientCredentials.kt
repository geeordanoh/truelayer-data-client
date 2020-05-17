package truelayer.data.auth.domain

data class ClientCredentials(
        val grantType: GrantType,
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String,
        val code: String,
        val codeVerifier: String?
)