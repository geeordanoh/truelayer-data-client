package truelayer.data.auth.domain

enum class GrantType(private val value: String) {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token")
}