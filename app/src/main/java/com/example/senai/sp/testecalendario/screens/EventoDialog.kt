package com.example.senai.sp.testecalendario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.senai.sp.testecalendario.viewmodel.CalendarioViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventoDialogComApi(
    viewModel: CalendarioViewModel,
    dataSelecionada: LocalDate = LocalDate.now(),
    onDismiss: () -> Unit
) {
    var dia by remember { mutableStateOf(dataSelecionada.dayOfMonth.toString()) }
    var mes by remember { mutableStateOf(dataSelecionada.monthValue.toString()) }
    var ano by remember { mutableStateOf(dataSelecionada.year.toString()) }
    var hora by remember { mutableStateOf("12:00") }
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var alarme by remember { mutableStateOf(false) }
    var corSelecionada by remember { mutableStateOf("#E57373") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coresDisponiveis = listOf(
        "#E57373", // Vermelho
        "#FFB74D", // Laranja
        "#FFF176", // Amarelo
        "#81C784", // Verde
        "#64B5F6", // Azul
        "#9575CD", // Roxo
        "#BA68C8", // Rosa
        "#4FC3F7"  // Ciano
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF7986CB)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Data e Horário",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campos de data
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = dia,
                        onValueChange = { if (it.length <= 2) dia = it },
                        label = { Text("DIA") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = mes,
                        onValueChange = { if (it.length <= 2) mes = it },
                        label = { Text("MÊS") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = ano,
                        onValueChange = { if (it.length <= 4) ano = it },
                        label = { Text("ANO") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Seletor de cor e hora
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Cor",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            modifier = Modifier.size(120.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(coresDisponiveis) { cor ->
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            Color(android.graphics.Color.parseColor(cor)),
                                            CircleShape
                                        )
                                        .border(
                                            width = if (cor == corSelecionada) 2.dp else 0.dp,
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                        .clickable { corSelecionada = cor }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = hora,
                        onValueChange = { hora = it },
                        modifier = Modifier.width(100.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = "Título",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = { Text("Escreva o título") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Nota
                Text(
                    text = "descricao",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    placeholder = { Text("Descreva o evento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Alarme
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alarme",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = alarme,
                        onCheckedChange = { alarme = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF4CAF50)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão Salvar
                Button(
                    onClick = {
                        if (titulo.isNotEmpty() && dia.isNotEmpty() && mes.isNotEmpty() && ano.isNotEmpty()) {
                            val data = LocalDate.of(ano.toInt(), mes.toInt(), dia.toInt())

                            viewModel.criarEvento(
                                titulo = titulo,
                                descricao = descricao,
                                data = data,
                                hora = hora,
                                cor = corSelecionada,
                                alarme = alarme,
                                onSuccess = {
                                    onDismiss()
                                },
                                onError = { error ->
                                    errorMessage = error
                                    showError = true
                                }
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Salvar",
                        color = Color(0xFF7986CB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}