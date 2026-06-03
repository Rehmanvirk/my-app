package com.app.calllogs.di.network

import com.app.calllogs.utils.TokenStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CookieTokenExtractorInterceptor(
    private val tokenStore: TokenStore,
    private val cookieName: String = "access_token" // change if your cookie name differs
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        // Read ALL Set-Cookie headers
        val setCookies = response.headers("Set-Cookie")
        val token = extractCookieValue(setCookies, cookieName)

        if (!token.isNullOrBlank()) {
            runBlocking { tokenStore.saveAccessToken(token) }
        }

        return response
    }

    private fun extractCookieValue(setCookies: List<String>, name: String): String? {
        // Example Set-Cookie: access_token=abc123; Path=/; HttpOnly; Secure
        for (header in setCookies) {
            val parts = header.split(";")
            val first = parts.firstOrNull()?.trim() ?: continue
            if (first.startsWith("$name=")) return first.substringAfter("$name=")
        }
        return null
    }
}