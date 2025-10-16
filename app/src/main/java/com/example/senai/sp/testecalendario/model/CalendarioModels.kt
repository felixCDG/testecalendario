package com.example.senai.sp.testecalendario.model


import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime

// Modelo para enviar dados para a API (POST/PUT)
data class CalendarioRequest(
    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("descricao")
    val descricao: String?,

    @SerializedName("data_calendario")
    val dataCalendario: String, // Formato: "YYYY-MM-DD"

    @SerializedName("hora_calendario")
    val horaCalendario: String, // Formato: "HH:mm:ss"

    @SerializedName("cor")
    val cor: String, // Formato: "#RRGGBB"

    @SerializedName("alarme_ativo")
    val alarmeAtivo: Int
)

// Modelo para receber dados da API (GET)
data class CalendarioResponse(
    @SerializedName("id_calendario")
    val idCalendario: Int,

    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("descricao")
    val descricao: String?,

    @SerializedName("data_calendario")
    val dataCalendario: String,

    @SerializedName("hora_calendario")
    val horaCalendario: String,

    @SerializedName("cor")
    val cor: String,

    @SerializedName("alarme_ativo")
    val alarmeAtivo: Int
)

// Modelo para uso na UI
data class EventoUI(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val data: LocalDate,
    val hora: LocalTime,
    val cor: String,
    val alarme: Boolean
)

// Função de extensão para converter CalendarioResponse em EventoUI
fun CalendarioResponse.toEventoUI(): EventoUI {
    return EventoUI(
        id = this.idCalendario,
        titulo = this.titulo,
        descricao = this.descricao ?: "",
        data = LocalDate.parse(this.dataCalendario),
        hora = LocalTime.parse(this.horaCalendario),
        cor = this.cor,
        alarme = this.alarmeAtivo == 1
    )
}

// Resposta padrão da API
data class ApiResponse<T>(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: T?
)
