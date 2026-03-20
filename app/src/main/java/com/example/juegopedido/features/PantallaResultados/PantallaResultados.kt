package com.example.juegopedido.features.PantallaResultados


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*


@Composable
fun PantallaResultados(nombre: String, tiempo: Long, onReiniciar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🏆 ¡RESULTADOS! 🏆", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color(0xFF388E3C))

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Entrenador:", fontSize = 16.sp)
                Text(nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Tiempo total:", fontSize = 16.sp)
                Text("$tiempo segundos", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF3761A8))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onReiniciar,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCC00))
        ) {
            Text("VOLVER AL MENÚ", color = Color(0xFF3761A8), fontWeight = FontWeight.Bold)
        }
    }
}