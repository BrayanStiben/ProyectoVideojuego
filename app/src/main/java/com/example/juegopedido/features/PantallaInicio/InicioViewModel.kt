package com.example.juegopedido.features.PantallaInicio


    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel

    class InicioViewModel : ViewModel() {
        // El estado vive aquí ahora
        var nombreInput by mutableStateOf("")
            private set // Solo el ViewModel puede modificarlo directamente

        fun onNombreCambiado(nuevoNombre: String) {
            nombreInput = nuevoNombre
        }

        // Lógica simple: ¿es válido el nombre?
        val esNombreValido: Boolean
            get() = nombreInput.isNotBlank()
    }
