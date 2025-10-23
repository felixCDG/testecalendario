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

    suspend fun getAllEventos(): List<EventoUI> = withContext(Dispatchers.IO) {
        val response = api.getAllEventos()
        response.dateCalender.map { it.toEventoUI() }
    }

    suspend fun criarEvento(request: CalendarioRequest): EventoUI = withContext(Dispatchers.IO) {
        val response = api.createEvento(request)
        response.toEventoUI()
    }

    suspend fun deletarEvento(id: Int): Unit = withContext(Dispatchers.IO) {
        api.deleteEvento(id)
    }

    suspend fun createEvento(
        idUser: Int,
        titulo: String,
        descricao: String,
        dataCalendario: String,
        horaCalendario: String,
        cor: String,
        alarmeAtivo: Boolean
    ): EventoUI = withContext(Dispatchers.IO) {
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
        response.toEventoUI()
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
    ): EventoUI = withContext(Dispatchers.IO) {
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
        response.toEventoUI()
    }
}
