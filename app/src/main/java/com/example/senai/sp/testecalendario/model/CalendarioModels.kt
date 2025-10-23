package com.example.senai.sp.testecalendario.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class CalendarioRequest(
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
    val cor: String?,

    @SerializedName("alarme_ativo")
    val alarmeAtivo: Int
)

data class CalendarioResponse(
    @SerializedName("id_calendario")
    val idCalendario: Int,

    @SerializedName("id_user")
    val idUser: Int,

    @SerializedName("titulo")
    val titulo: String?,

    @SerializedName("descricao")
    val descricao: String?,

    @SerializedName("data_calendario")
    val dataCalendario: String,

    @SerializedName("hora_calendario")
    val horaCalendario: String,

    @SerializedName("cor")
    val cor: String?,

    @SerializedName("alarme_ativo")
    val alarmeAtivo: Int
)

data class CalendarioApiResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("items")
    val items: Int,

    @SerializedName("dateCalender")
    val dateCalender: List<CalendarioResponse>
)

data class EventoUI(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val data: LocalDate,
    val hora: LocalTime,
    val cor: String?,
    val alarme: Boolean
)

fun CalendarioResponse.toEventoUI(): EventoUI {
    val data = try {
        LocalDate.parse(this.dataCalendario.substring(0, 10))
    } catch (e: Exception) {
        LocalDate.now()
    }

    val hora = try {
        val horaString = this.horaCalendario.substring(11, 19)
        LocalTime.parse(horaString)
    } catch (e: Exception) {
        LocalTime.now()
    }

    return EventoUI(
        id = this.idCalendario,
        titulo = this.titulo ?: "Sem título",
        descricao = this.descricao ?: "",
        data = data,
        hora = hora,
        cor = this.cor,
        alarme = this.alarmeAtivo == 1
    )
}
