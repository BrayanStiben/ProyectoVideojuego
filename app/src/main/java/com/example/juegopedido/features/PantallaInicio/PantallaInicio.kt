package com.example.juegopedido.features.PantallaInicio


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*


@Composable
fun PantallaInicio(onEmpezarJuego: (String) -> Unit) {
    var nombreInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Poke-Memory", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF3761A8))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "¡Hola! Encuentra las 8 parejas de Pokémon. Escribe tu nombre para comenzar.",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombreInput,
            onValueChange = { nombreInput = it },
            label = { Text("Nombre del Entrenador") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { if (nombreInput.isNotBlank()) onEmpezarJuego(nombreInput) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = nombreInput.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00))
        ) {
            Text("¡A JUGAR!", color = Color(0xFF3761A8), fontWeight = FontWeight.Bold)
        }
    }
}