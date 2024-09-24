package com.example.lern.tools

import android.util.Log.d
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

object InternetHelper {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
        return try {
            apiCall()
        } catch (e: RedirectResponseException) {
            // 3xx
            d("API Client", "Redirection Error: ${e.message}")
            null
        } catch (e: ClientRequestException) {
            // 4xx
            d("API Client", "Client Error: ${e.message}")
            null
        } catch (e: ServerResponseException) {
            // 5xx
            d("API Client", "Server Error: ${e.message}")
            null
        } catch (e: Exception) {
            d("API Client", "Error: ${e.message}")
            null
        }
    }
}