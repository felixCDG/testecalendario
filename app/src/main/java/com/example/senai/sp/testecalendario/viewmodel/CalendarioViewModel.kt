package com.example.senai.sp.testecalendario.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.senai.sp.testecalendario.model.CalendarioRequest
import com.example.senai.sp.testecalendario.model.EventoUI
import com.example.senai.sp.testecalendario.repository.CalendarioRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CalendarioUiState(
    val eventos: List<EventoUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CalendarioViewModel : ViewModel() {
    private val repository = CalendarioRepository()

    private val _uiState = mutableStateOf(CalendarioUiState())
    val uiState: State<CalendarioUiState> = _uiState

    fun carregarEventos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val eventos = repository.getAllEventos()
                _uiState.value = _uiState.value.copy(
                    eventos = eventos,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Erro ao carregar eventos"
                )
            }
        }
    }

    fun criarEvento(
        titulo: String,
        descricao: String,
        data: LocalDate,
        hora: String,
        cor: String,
        alarme: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Formatar data no formato esperado pela API (YYYY-MM-DD)
                val dataFormatada = data.format(DateTimeFormatter.ISO_LOCAL_DATE)

                // Formatar hora no formato esperado pela API (HH:mm:ss)
                val horaFormatada = if (hora.contains(":")) {
                    if (hora.split(":").size == 2) "$hora:00" else hora
                } else {
                    "12:00:00"
                }

                val request = CalendarioRequest(
                    idUser = 2, // TODO: Substituir pelo ID do usuário logado
                    titulo = titulo,
                    descricao = descricao.ifBlank { null },
                    dataCalendario = dataFormatada,
                    horaCalendario = horaFormatada,
                    cor = cor,
                    alarmeAtivo = if (alarme) 1 else 0
                )

                repository.criarEvento(request)
                carregarEventos()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Erro ao criar evento")
            }
        }
    }

    fun criarEvento(request: CalendarioRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.criarEvento(request)
                carregarEventos()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Erro ao criar evento")
            }
        }
    }

    fun deletarEvento(id: Int) {
        viewModelScope.launch {
            try {
                repository.deletarEvento(id)
                carregarEventos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Erro ao deletar evento"
                )
            }
        }
    }

    fun getEventosPorData(data: LocalDate): List<EventoUI> {
        return _uiState.value.eventos.filter { it.data == data }
    }
}
