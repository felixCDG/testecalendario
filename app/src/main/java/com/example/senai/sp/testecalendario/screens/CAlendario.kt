package com.example.senai.sp.testecalendario.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.senai.sp.testecalendario.model.EventoUI
import com.example.senai.sp.testecalendario.viewmodel.CalendarioViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarioScreen(
    navegacao: NavController,
    viewModel: CalendarioViewModel = viewModel()
) {
    var mesAtual by remember { mutableStateOf(YearMonth.now()) }
    var mostrarDialogEvento by remember { mutableStateOf(false) }
    var dataSelecionada by remember { mutableStateOf(LocalDate.now()) }

    val uiState by viewModel.uiState
    val eventosHoje = viewModel.getEventosPorData(LocalDate.now())

    // Carrega eventos apenas uma vez quando a tela é criada
    LaunchedEffect(Unit) {
        viewModel.carregarEventos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendário") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5E5E5E),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogEvento = true },
                containerColor = Color(0xFF7986CB)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Adicionar evento",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Cabeçalho do mês
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { mesAtual = mesAtual.minusMonths(1) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Mês anterior")
                    }

                    Text(
                        text = "${mesAtual.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))} ${mesAtual.year}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = { mesAtual = mesAtual.plusMonths(1) }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Próximo mês")
                    }
                }

                // Grade do calendário
                CalendarioGrid(
                    mesAtual = mesAtual,
                    onDiaSelecionado = { dataSelecionada = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Seção "Hoje"
                Text(
                    text = "Hoje",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mostrar erro se houver
                if (uiState.error != null) {
                    Text(
                        text = uiState.error ?: "",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                // Lista de eventos
                if (eventosHoje.isEmpty() && !uiState.isLoading) {
                    Text(
                        text = "Nenhum evento para hoje",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(eventosHoje) { evento ->
                            EventoCard(
                                evento = evento,
                                onDelete = {
                                    viewModel.deletarEvento(evento.id)
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // Loading overlay
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF7986CB))
                }
            }
        }
    }

    if (mostrarDialogEvento) {
        EventoDialogComApi(
            viewModel = viewModel,
            onDismiss = { mostrarDialogEvento = false }
        )
    }
}

@Composable
fun CalendarioGrid(
    mesAtual: YearMonth,
    onDiaSelecionado: (LocalDate) -> Unit
) {
    val primeiroDia = mesAtual.atDay(1)
    val diasNoMes = mesAtual.lengthOfMonth()
    val primeiroDiaSemana = primeiroDia.dayOfWeek.value % 7

    val diasSemana = listOf("Do", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Cabeçalho dos dias da semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            diasSemana.forEach { dia ->
                Text(
                    text = dia,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Grade de dias
        var diaAtual = 1
        for (semana in 0..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (diaSemana in 0..6) {
                    if ((semana == 0 && diaSemana < primeiroDiaSemana) || diaAtual > diasNoMes) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        val dia = diaAtual
                        val data = mesAtual.atDay(dia)
                        val isHoje = data == LocalDate.now()

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clickable { onDiaSelecionado(data) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dia.toString(),
                                fontSize = 14.sp,
                                color = if (isHoje) Color(0xFFE57373) else Color.Black,
                                fontWeight = if (isHoje) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        diaAtual++
                    }
                }
            }
        }
    }
}

@Composable
fun EventoCard(
    evento: EventoUI,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7986CB)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            Color(android.graphics.Color.parseColor(evento.cor)),
                            CircleShape
                        )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = evento.titulo,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = evento.hora.toString(),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Deletar",
                    tint = Color(0xFFE57373)
                )
            }
        }
    }
}