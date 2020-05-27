package truelayer.data.rest

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.jackson.responseObject

abstract class FuelRestClientCommons {

    inline fun <reified T : Any> issueGetRequest(fuelManager: FuelManager, url: String, vararg headers: Pair<String, String>): T {
        val (_, response, result) = fuelManager
                .get(url)
                .header(*headers)
                .responseObject<T>()
        return result.fold<T>({ return it }, { throw InvalidRequestException("Request failed with status code ${response.statusCode}", it) })

    }

    fun issueDeleteRequest(fuelManager: FuelManager, url: String, vararg headers: Pair<String, String>) {
        val (_, response, _) = fuelManager
                .delete(url)
                .header(*headers)
                .response()
        if (response.statusCode != 200) throw InvalidRequestException("Request failed with status code ${response.statusCode}")
    }

    inline fun <reified T : Any> issuePostRequestWithUrlEncoding(fuelManager: FuelManager, url: String, request: List<Pair<String, String>>, vararg headers: Pair<String, String>): T {
        val (_, response, result) = fuelManager
                .post(url, request)
                .header(*headers)
                .responseObject<T>()
        return result.fold<T>({ return it }, { throw InvalidRequestException("Request failed with status code ${response.statusCode}", it) })
    }

}