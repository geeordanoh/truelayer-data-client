package truelayer.data.auth.domain

data class TokenRequestParameters(
        val redirectUri: String,
        val code: String,
        val codeVerifier: String? = null
)