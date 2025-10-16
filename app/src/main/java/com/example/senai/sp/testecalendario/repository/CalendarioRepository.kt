package com.example.senai.sp.testecalendario.repository

import com.example.senai.sp.testecalendario.model.CalendarioRequest
import com.example.senai.sp.testecalendario.model.EventoUI
import com.example.senai.sp.testecalendario.model.toEventoUI
import com.example.senai.sp.testecalendario.service.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarioRepository {

    private val api = RetrofitClient.calendarioApi

    suspend fun getAllEventos(): Result<List<EventoUI>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllCalendarios()
            if (response.isSuccessful && response.body()?.status == true) {
                val eventos = response.body()?.data?.map { it.toEventoUI() } ?: emptyList()
                Result.success(eventos)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Erro ao buscar eventos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEventoById(id: Int): Result<EventoUI> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCalendarioById(id)
            if (response.isSuccessful && response.body()?.status == true) {
                val evento = response.body()?.data?.toEventoUI()
                if (evento != null) {
                    Result.success(evento)
                } else {
                    Result.failure(Exception("Evento não encontrado"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Erro ao buscar evento"))
            }
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

            val response = api.createCalendario(request)
            if (response.isSuccessful && response.body()?.status == true) {
                val evento = response.body()?.data?.toEventoUI()
                if (evento != null) {
                    Result.success(evento)
                } else {
                    Result.failure(Exception("Erro ao criar evento"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Erro ao criar evento"))
            }
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

            val response = api.updateCalendario(id, request)
            if (response.isSuccessful && response.body()?.status == true) {
                val evento = response.body()?.data?.toEventoUI()
                if (evento != null) {
                    Result.success(evento)
                } else {
                    Result.failure(Exception("Erro ao atualizar evento"))
                }
            } else {
                Result.failure(Exception(response.body()?.message ?: "Erro ao atualizar evento"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteEvento(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = api.deleteCalendario(id)
            if (response.isSuccessful && response.body()?.status == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Erro ao deletar evento"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}