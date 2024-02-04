package com.example.foodylearn.di

import com.example.foodylearn.util.Constants.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val url: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val requestBuilder: Request.Builder = originalRequest.newBuilder().url(url)
        val modifiedRequest: Request = requestBuilder.build()
        return chain.proceed(modifiedRequest)
    }
}