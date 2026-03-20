package com.example.juegopedido.features.PantallaInicio

import com.example.juegopedido.R


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun PantallaInicio(
    viewModel: InicioViewModel = viewModel(), // Inyectamos el VM
    onEmpezarJuego: (String) -> Unit
) {
    // Obtenemos los valores desde el ViewModel
    val nombre = viewModel.nombreInput
    val esValido = viewModel.esNombreValido

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Poke-Memory", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3761A8))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "¡Hola! Encuentra las 8 parejas de Pokémon. Escribe tu nombre para comenzar.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.onNombreCambiado(it) }, // Notificamos al VM
            label = { Text("Nombre del Entrenador") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onEmpezarJuego(nombre) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = esValido, // Usamos la validación del VM
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00))
        ) {
            Text("¡A JUGAR!", color = Color(0xFF3761A8), fontWeight = FontWeight.Bold)
        }
    }
}
