package com.app.calllogs.di.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import kotlin.collections.find

class AuthCookieJar() : CookieJar {
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // Extract the access token (or the full cookie) from the list
        // and save it to DataStore using a CoroutineScope.
        cookies.find { it.name == "access_token" }?.let { accessTokenCookie ->
            // Use a coroutine scope to save asynchronously
            // Example: GlobalScope.launch { dataStore.saveAccessToken(accessTokenCookie.value) }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // Load the saved access token from DataStore and build a Cookie object to return.
        // This usually requires a blocking call or specific OkHttp setup to handle suspend functions.
        // Example: val token = runBlocking { dataStore.getAccessToken() }
        // if (token != null) { return listOf(makeTokenCookie(url.host, token)) }
        return emptyList()
    }

    // Helper function to create a cookie
    // private fun makeTokenCookie(domain: String, token: String): Cookie { ... }
}