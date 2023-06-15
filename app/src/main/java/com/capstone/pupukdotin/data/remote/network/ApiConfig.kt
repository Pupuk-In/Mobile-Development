package com.capstone.pupukdotin.data.remote.network

import android.util.Log
import com.capstone.pupukdotin.utils.Constant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private var token = ""

    fun setToken(value: String) {
        token = value
    }

    fun removeToken() {
        token = ""
    }

    private fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if(request.header("No-Authentication") == null) {
                if(token.isNotEmpty()) {
                    val finalToken = "Bearer $token"
                    Log.d("ini_log_token", finalToken)
                    request = request.newBuilder()
                        .addHeader(Constant.AUTH_HEADER, finalToken)
                        .build()
                }
            }
            chain.proceed(request)
        }
    }

    private fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val requestHeader = request.newBuilder()
                .addHeader(Constant.ACCEPT_HEADER, Constant.APPLICATION_JSON)
                .build()
            chain.proceed(requestHeader)
        }

    }

    fun getApiService(): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeaderInterceptor())
            .addInterceptor(getAuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServices::class.java)
    }

    fun getTfService(): TfServices {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeaderInterceptor())
            .addInterceptor(getAuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.TF_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(TfServices::class.java)
    }

    fun getProdService(): ApiServices {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeaderInterceptor())
            .addInterceptor(getAuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServices::class.java)
    }

    fun getCommonServices(): CommonServices {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(getHeaderInterceptor())
            .addInterceptor(getAuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.PROD_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(CommonServices::class.java)
    }
}