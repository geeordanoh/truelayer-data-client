package truelayer.data.auth.domain


data class AccessToken(
        val accessTokenJWT: String,
        val expiresIn: String,
        val tokenType: String,
        val refreshToken: String
)