package com.example.juegopedido.features.MemoryCards

data class CartaMemoria(
    val id: Int,
    val imagenRes: Int, // Esta es la referencia a R.drawable.pokemonX
    val estaVolteada: Boolean = false,
    val estaEmparejada: Boolean = false
)