package truelayer.data.rest

data class RestClientConfiguration(
        val useSandbox: Boolean = false,
        val timeoutInMillisecond: Int = 10000,
        val timeoutReadInMillisecond: Int = 10000
)