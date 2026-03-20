package com.example.juegopedido.features.PantallaResultados
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import androidx.room3.util.copy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResultadosViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ResultadosState())
    val uiState: StateFlow<ResultadosState> = _uiState.asStateFlow()

    fun actualizarDatos(nombre: String, tiempoEntrada: Long) {
        // CORRECCIÓN: Si el tiempo es menor a 1000,
        // es probable que nos estén pasando segundos en lugar de ms.
        val tiempoMs = if (tiempoEntrada < 1000 && tiempoEntrada > 0) {
            tiempoEntrada * 1000
        } else {
            tiempoEntrada
        }

        val totalSegundos = tiempoMs / 1000
        val minutos = totalSegundos / 60
        val segundos = totalSegundos % 60
        val centesimas = (tiempoMs % 1000) / 10

        // Esto te dará 05:42:15 (5 min, 42 seg, 15 centésimas)
        val tiempoTexto = String.format("%02d:%02d:%02d", minutos, segundos, centesimas)

        _uiState.update {
            it.copy(
                nombre = nombre.uppercase().trim(),
                tiempoFormateado = tiempoTexto
            )
        }
    }
}