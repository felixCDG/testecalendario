package com.example.senai.sp.testecalendario.repository

import com.example.senai.sp.testecalendario.model.CalendarioRequest
import com.example.senai.sp.testecalendario.model.EventoUI
import com.example.senai.sp.testecalendario.model.toEventoUI
import com.example.senai.sp.testecalendario.service.CalendarioApiService
import com.example.senai.sp.testecalendario.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarioRepository {

    private val api: CalendarioApiService =
        RetrofitClient.instance.create(CalendarioApiService::class.java)

    suspend fun getAllEventos(): Result<List<EventoUI>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllEventos()
            val eventos = response.dateCalender.map { it.toEventoUI() }
            Result.success(eventos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createEvento(
        idUser: Int,
        titulo: String,
        descricao: String,
        dataCalendario: String,
        horaCalendario: String,
        cor: String,
        alarmeAtivo: Boolean
    ): Result<EventoUI> = withContext(Dispatchers.IO) {
        try {
            val request = CalendarioRequest(
                idUser = idUser,
                titulo = titulo,
                descricao = descricao,
                dataCalendario = dataCalendario,
                horaCalendario = horaCalendario,
                cor = cor,
                alarmeAtivo = if (alarmeAtivo) 1 else 0
            )

            val response = api.createEvento(request)
            Result.success(response.toEventoUI())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateEvento(
        id: Int,
        idUser: Int,
        titulo: String,
        descricao: String,
        dataCalendario: String,
        horaCalendario: String,
        cor: String,
        alarmeAtivo: Boolean
    ): Result<EventoUI> = withContext(Dispatchers.IO) {
        try {
            val request = CalendarioRequest(
                idUser = idUser,
                titulo = titulo,
                descricao = descricao,
                dataCalendario = dataCalendario,
                horaCalendario = horaCalendario,
                cor = cor,
                alarmeAtivo = if (alarmeAtivo) 1 else 0
            )

            val response = api.updateEvento(id, request)
            Result.success(response.toEventoUI())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteEvento(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            api.deleteEvento(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
