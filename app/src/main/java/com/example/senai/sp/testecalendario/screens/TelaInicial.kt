package com.example.senai.sp.testecalendario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun HomeScreen(navController: NavController?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2FF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeroSection()
            Spacer(Modifier.height(24.dp))
            CalendarSection(navController)
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF103D8C))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "CUIDAR É AMAR",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Descubra como nossos serviços ajudam você a acompanhar cada momento do seu bebê com mais segurança e carinho.",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
        ) {
            Text("Explorar")
        }
    }
}

@Composable
fun CalendarSection(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(Color(0xFF4A90E2)),
            modifier = Modifier
                .padding(8.dp)
                .width(260.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Seu calendário de Rotinas",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "Acompanhe consultas, vacinas e lembretes importantes para o bem-estar do seu bebê.",
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))

        MiniCalendar()

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { navController?.navigate("calendario") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7986CB)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Ver Calendário", modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
        }
    }
}

@Composable
fun MiniCalendar() {
    val mesAtual = YearMonth.now()
    val primeiroDia = mesAtual.atDay(1)
    val diasNoMes = mesAtual.lengthOfMonth()
    val primeiroDiaSemana = primeiroDia.dayOfWeek.value % 7
    val hoje = LocalDate.now()

    // Eventos de exemplo (você pode conectar com dados reais depois)
    val eventosExemplo = mapOf(
        1 to Color(0xFFE91E63), // Rosa - Consulta
        5 to Color(0xFF4CAF50), // Verde - Vacina
        10 to Color(0xFFFF9800) // Laranja - Lembrete
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(Color(0xFFE8EAF6)),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Cabeçalho dos dias da semana
            val diasSemana = listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                diasSemana.forEach { dia ->
                    Text(
                        text = dia.take(3),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5E5E5E)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Grade do calendário
            var diaAtual = 1
            for (semana in 0..5) {
                if (diaAtual > diasNoMes) break

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (diaSemana in 0..6) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if ((semana == 0 && diaSemana < primeiroDiaSemana) || diaAtual > diasNoMes) {
                                // Célula vazia
                            } else {
                                val dia = diaAtual
                                val isHoje = mesAtual.atDay(dia) == hoje
                                val temEvento = eventosExemplo.containsKey(dia)

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = dia.toString(),
                                        fontSize = 14.sp,
                                        fontWeight = if (isHoje) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isHoje) Color(0xFF7986CB) else Color.Black
                                    )

                                    if (temEvento) {
                                        Spacer(Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(eventosExemplo[dia] ?: Color.Gray)
                                        )
                                    }
                                }
                                diaAtual++
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Legenda dos eventos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendaItem(Color(0xFFE91E63), "Consulta")
                LegendaItem(Color(0xFF4CAF50), "Vacina")
                LegendaItem(Color(0xFFFF9800), "Lembrete")
            }
        }
    }
}

@Composable
fun LegendaItem(cor: Color, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(cor)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = texto,
            fontSize = 10.sp,
            color = Color(0xFF5E5E5E)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = null)
}

@Preview(showBackground = true)
@Composable
fun CalendarSectionPreview() {
    CalendarSection(navController = null)
}

@Preview(showBackground = true)
@Composable
fun MiniCalendarPreview() {
    MiniCalendar()
}
