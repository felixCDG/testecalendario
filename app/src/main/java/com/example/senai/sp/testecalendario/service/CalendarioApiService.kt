package com.example.senai.sp.testecalendario.service

import com.example.senai.sp.testecalendario.model.CalendarioResponse
import  com.example.senai.sp.testecalendario.model.ApiResponse
import  com.example.senai.sp.testecalendario.model.CalendarioRequest
import retrofit2.Response
import retrofit2.http.*

interface CalendarioApiService {

    @Headers("Content-Type: application/json")
    @GET("calenders")
    suspend fun getAllCalendarios(): Response<ApiResponse<List<CalendarioResponse>>>

    @Headers("Content-Type: application/json")
    @GET("calender/{id}")
    suspend fun getCalendarioById(
        @Path("id") id: Int
    ): Response<ApiResponse<CalendarioResponse>>

    @Headers("Content-Type: application/json")
    @POST("calender/cadastro")
    suspend fun createCalendario(
        @Body calendario: CalendarioRequest
    ): Response<ApiResponse<CalendarioResponse>>

    @Headers("Content-Type: application/json")
    @PUT("calender/{id}")
    suspend fun updateCalendario(
        @Path("id") id: Int,
        @Body calendario: CalendarioRequest
    ): Response<ApiResponse<CalendarioResponse>>

    @Headers("Content-Type: application/json")
    @DELETE("calender/{id}")
    suspend fun deleteCalendario(
        @Path("id") id: Int
    ): Response<ApiResponse<Unit>>
}