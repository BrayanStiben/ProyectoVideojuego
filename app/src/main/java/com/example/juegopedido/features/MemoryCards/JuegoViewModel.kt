package com.example.juegopedido.features.MemoryCards
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegopedido.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow



class JuegoViewModel : ViewModel() {
    private val _cartas = MutableStateFlow<List<CartaMemoria>>(emptyList())
    val cartas = _cartas.asStateFlow()

    // Estado para el contador de parejas
    private val _parejasEncontradas = MutableStateFlow(0)
    val parejasEncontradas = _parejasEncontradas.asStateFlow()

    private val _eventos = MutableSharedFlow<EventoJuego>()
    val eventos = _eventos.asSharedFlow()

    private var indicePrimeraCarta: Int? = null
    private var bloqueoTablero: Boolean = false

    sealed class EventoJuego {
        data class MostrarSnackbar(val mensaje: String, val esError: Boolean) : EventoJuego()
    }

    init {
        prepararJuego()
    }

    fun prepararJuego() {
        // Reiniciamos estados lógicos
        indicePrimeraCarta = null
        bloqueoTablero = false
        _parejasEncontradas.value = 0

        val imagenes = listOf(
            R.drawable.pokemon1, R.drawable.pokemon2,
            R.drawable.pokemon3, R.drawable.pokemon4,
            R.drawable.pokemon5, R.drawable.pokemon6,
            R.drawable.pokemon7, R.drawable.pokemon8
        )
        val listaMezclada = (imagenes + imagenes).shuffled().mapIndexed { i, res ->
            CartaMemoria(id = i, imagenRes = res)
        }
        _cartas.value = listaMezclada
    }

    fun seleccionarCarta(posicion: Int) {
        val lista = _cartas.value.toMutableList()
        if (bloqueoTablero || lista[posicion].estaVolteada || lista[posicion].estaEmparejada) return

        if (indicePrimeraCarta == null) {
            indicePrimeraCarta = posicion
            lista[posicion] = lista[posicion].copy(estaVolteada = true)
            _cartas.value = lista
        } else {
            val primerIdx = indicePrimeraCarta!!
            if (primerIdx == posicion) return

            lista[posicion] = lista[posicion].copy(estaVolteada = true)
            _cartas.value = lista
            verificarPareja(primerIdx, posicion)
            indicePrimeraCarta = null
        }
    }

    private fun verificarPareja(idx1: Int, idx2: Int) {
        viewModelScope.launch {
            val lista = _cartas.value.toMutableList()
            if (lista[idx1].imagenRes == lista[idx2].imagenRes) {
                lista[idx1] = lista[idx1].copy(estaEmparejada = true)
                lista[idx2] = lista[idx2].copy(estaEmparejada = true)
                _cartas.value = lista

                // Aumentamos el score
                _parejasEncontradas.value += 1

                _eventos.emit(EventoJuego.MostrarSnackbar("¡Pareja encontrada! 🎉", false))
            } else {
                bloqueoTablero = true
                _eventos.emit(EventoJuego.MostrarSnackbar("No son iguales ❌", true))
                delay(1000)
                lista[idx1] = lista[idx1].copy(estaVolteada = false)
                lista[idx2] = lista[idx2].copy(estaVolteada = false)
                _cartas.value = lista
                bloqueoTablero = false
            }
        }
    }
}