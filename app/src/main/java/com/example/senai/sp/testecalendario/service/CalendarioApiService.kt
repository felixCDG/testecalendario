
package com.example.senai.sp.testecalendario.service

import com.example.senai.sp.testecalendario.model.CalendarioApiResponse
import com.example.senai.sp.testecalendario.model.CalendarioRequest
import com.example.senai.sp.testecalendario.model.CalendarioResponse
import retrofit2.http.*

interface CalendarioApiService {

    @Headers("Content-Type: application/json")
    @GET("calenders")
    suspend fun getAllEventos(): CalendarioApiResponse

    @Headers("Content-Type: application/json")
    @POST("calender/cadastro")
    suspend fun createEvento(@Body request: CalendarioRequest): CalendarioResponse

    @Headers("Content-Type: application/json")
    @PUT("calender/{id}")
    suspend fun updateEvento(
        @Path("id") id: Int,
        @Body request: CalendarioRequest
    ): CalendarioResponse

    @Headers("Content-Type: application/json")
    @DELETE("calender/{id}")
    suspend fun deleteEvento(@Path("id") id: Int)
}
