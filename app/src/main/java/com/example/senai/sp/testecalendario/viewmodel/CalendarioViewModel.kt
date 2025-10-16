package com.example.senai.sp.testecalendario.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.senai.sp.testecalendario.model.EventoUI
import com.example.senai.sp.testecalendario.repository.CalendarioRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

data class CalendarioUiState(
    val eventos: List<EventoUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CalendarioViewModel : ViewModel() {

    private val repository = CalendarioRepository()

    private val _uiState = mutableStateOf(CalendarioUiState())
    val uiState: State<CalendarioUiState> = _uiState

    private var currentUserId: Int = 1

    // <CHANGE> Removido o carregamento automático do init
    // Agora só carrega eventos quando explicitamente solicitado

    fun setUserId(userId: Int) {
        currentUserId = userId
        carregarEventos()
    }

    fun carregarEventos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            repository.getAllEventos()
                .onSuccess { eventos ->
                    _uiState.value = _uiState.value.copy(
                        eventos = eventos,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    // <CHANGE> Só mostra erro se não for uma lista vazia
                    // Se a API retornar lista vazia, não é erro
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = if (exception.message?.contains("não encontrado") == true) {
                            null // Não mostra erro para lista vazia
                        } else {
                            exception.message ?: "Erro desconhecido"
                        }
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
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.createEvento(
                idUser = currentUserId,
                titulo = titulo,
                descricao = descricao,
                dataCalendario = data.toString(),
                horaCalendario = "$hora:00",
                cor = cor,
                alarmeAtivo = alarme
            )
                .onSuccess {
                    // <CHANGE> Após criar, recarrega a lista de eventos
                    carregarEventos()
                    onSuccess()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    onError(exception.message ?: "Erro ao criar evento")
                }
        }
    }

    fun deletarEvento(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.deleteEvento(id)
                .onSuccess {
                    carregarEventos()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro ao deletar evento"
                    )
                }
        }
    }

    fun getEventosPorData(data: LocalDate): List<EventoUI> {
        return _uiState.value.eventos.filter { it.data == data }
    }

    // <CHANGE> Função para limpar erros
    fun limparErro() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}