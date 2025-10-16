package com.example.senai.sp.testecalendario.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // ALTERE PARA O SEU ENDEREÇO DE API
    // Para emulador Android: use "http://10.0.2.2:3000/"
    // Para dispositivo físico: use o IP da sua máquina "http://192.168.x.x:3000/"
    private const val BASE_URL = "http://10.0.2.2:3030/v1/sosbaby/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val calendarioApi: CalendarioApiService = retrofit.create(CalendarioApiService::class.java)
}