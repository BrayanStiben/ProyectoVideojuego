package com.example.juegopedido.features.PantallaResultados

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class ResultadosViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ResultadosState())
    val uiState: StateFlow<ResultadosState> = _uiState.asStateFlow()

    fun actualizarDatos(nombre: String, tiempoMs: Long) {
        // Aseguramos que el tiempo no sea negativo
        val tiempoTratado = if (tiempoMs < 0) 0L else tiempoMs
        
        val totalSegundos = tiempoTratado / 1000
        val minutos = totalSegundos / 60
        val segundos = totalSegundos % 60
        val centesimas = (tiempoTratado % 1000) / 10

        // Formato estándar 00:00:00
        val tiempoTexto = String.format(Locale.getDefault(), "%02d:%02d:%02d", minutos, segundos, centesimas)

        _uiState.update {
            it.copy(
                nombre = nombre.uppercase().trim(),
                tiempoFormateado = tiempoTexto
            )
        }
    }
}
