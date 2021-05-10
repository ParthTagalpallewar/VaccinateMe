package com.cowin.vaccinateme.data.network

import com.cowin.vaccinateme.data.models.CowinCentersResponse
import com.cowin.vaccinateme.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {
    @GET("calendarByPin")
    suspend fun getAppointments(
        @Query("pincode") pincode: String,
        @Query("date") date: String
    ): Response<CowinCentersResponse>

    companion object {
        operator fun invoke(
        ): NetworkInterface {

            val loginInspector =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .header("User-Agent", "COOL APP 9000")
                            .build()
                    )
                }
                .addInterceptor(loginInspector)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NetworkInterface::class.java)
        }
    }
}