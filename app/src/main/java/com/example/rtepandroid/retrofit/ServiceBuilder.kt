package com.example.rtepandroid.retrofit

import android.content.Context
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private const val BASE_URL = "http://192.168.0.205:8000/"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val BEARER_PREFIX = "Bearer "
    private const val SHARED_PREFS_NAME = "AppPreferences"
    private const val KEY_AUTH_TOKEN = ""

    private lateinit var sharedPreferences: SharedPreferences

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = addAuthorizationHeader(originalRequest)
            chain.proceed(modifiedRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private fun addAuthorizationHeader(request: Request): Request {
//        val token = sharedPreferences.getString(KEY_AUTH_TOKEN, "") ?: ""
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg2MzkxMjQ5LCJpYXQiOjE2ODYzOTA5NDksImp0aSI6IjM3NjgyODE3NGFiMjRkYjg4Y2JiNjA2NzVmOGE1OTE5IiwidXNlcl9pZCI6MX0.O5WokwDg8C3DMuARAdS0A-6FNAp4ptZeUSi0i4m9NYw"
        val modifiedRequest = request.newBuilder()
            .header(AUTHORIZATION_HEADER, BEARER_PREFIX + token)
            .build()
        return modifiedRequest
    }

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun clearAuthToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
