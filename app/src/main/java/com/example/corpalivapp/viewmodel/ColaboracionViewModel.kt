package com.example.corpalivapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpalivapp.data.model.Colaboracion
import com.example.corpalivapp.data.repository.ColaboracionRepository
import com.example.corpalivapp.validacion.ColaboracionValidador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ColaboracionViewModel(
    private val repository: ColaboracionRepository
) : ViewModel() {

    val colaboraciones = repository.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Estado del formulario: solo el ViewModel lo modifica, la pantalla solo lo lee.
    private val _formulario = MutableStateFlow(FormularioColaboracionEstado())
    val formulario: StateFlow<FormularioColaboracionEstado> = _formulario.asStateFlow()

    // Cada cambio guarda el valor nuevo y lo valida en el momento.
    fun cambiarNombre(valor: String) {
        _formulario.value = _formulario.value.copy(
            nombre = valor,
            errorNombre = ColaboracionValidador.validarNombre(valor)
        )
    }

    fun cambiarOrganizacion(valor: String) {
        _formulario.value = _formulario.value.copy(
            organizacion = valor,
            errorOrganizacion = ColaboracionValidador.validarOrganizacion(valor)
        )
    }

    fun cambiarCorreo(valor: String) {
        _formulario.value = _formulario.value.copy(
            correo = valor,
            errorCorreo = ColaboracionValidador.validarCorreo(valor)
        )
    }

    fun cambiarTipo(valor: String) {
        _formulario.value = _formulario.value.copy(
            tipo = valor,
            errorTipo = ColaboracionValidador.validarTipo(valor)
        )
    }

    fun cambiarCantidad(valor: String) {
        _formulario.value = _formulario.value.copy(
            cantidadPersonas = valor,
            errorCantidad = ColaboracionValidador.validarCantidad(valor)
        )
    }

    fun cambiarFecha(valor: String) {
        _formulario.value = _formulario.value.copy(
            fecha = valor,
            errorFecha = ColaboracionValidador.validarFecha(valor)
        )
    }

    fun cambiarAceptaContacto(valor: Boolean) {
        _formulario.value = _formulario.value.copy(
            aceptaContacto = valor,
            errorAceptaContacto = ColaboracionValidador.validarAceptaContacto(valor)
        )
    }

    // Valida todos los campos; si no hay errores, guarda en Room y limpia el formulario.
    fun enviarFormulario() {
        val actual = _formulario.value
        val validado = actual.copy(
            errorNombre = ColaboracionValidador.validarNombre(actual.nombre),
            errorOrganizacion = ColaboracionValidador.validarOrganizacion(actual.organizacion),
            errorCorreo = ColaboracionValidador.validarCorreo(actual.correo),
            errorTipo = ColaboracionValidador.validarTipo(actual.tipo),
            errorCantidad = ColaboracionValidador.validarCantidad(actual.cantidadPersonas),
            errorFecha = ColaboracionValidador.validarFecha(actual.fecha),
            errorAceptaContacto = ColaboracionValidador.validarAceptaContacto(actual.aceptaContacto)
        )
        _formulario.value = validado

        if (tieneErrores(validado)) {
            return
        }

        val nuevaColaboracion = Colaboracion(
            nombreContacto = validado.nombre.trim(),
            organizacion = validado.organizacion.trim(),
            correo = validado.correo.trim(),
            tipoColaboracion = validado.tipo,
            cantidadPersonas = validado.cantidadPersonas.trim().toInt(),
            fechaContacto = validado.fecha.trim(),
            aceptaContacto = validado.aceptaContacto
        )

        viewModelScope.launch {
            repository.insertar(nuevaColaboracion)
            _formulario.value = FormularioColaboracionEstado(guardadoConExito = true)
        }
    }

    // La pantalla avisa que ya mostró la confirmación.
    fun ocultarMensajeExito() {
        _formulario.value = _formulario.value.copy(guardadoConExito = false)
    }

    private fun tieneErrores(estado: FormularioColaboracionEstado): Boolean {
        return estado.errorNombre != null ||
                estado.errorOrganizacion != null ||
                estado.errorCorreo != null ||
                estado.errorTipo != null ||
                estado.errorCantidad != null ||
                estado.errorFecha != null ||
                estado.errorAceptaContacto != null
    }

    fun insertar(colaboracion: Colaboracion) {
        viewModelScope.launch {
            repository.insertar(colaboracion)
        }
    }

    fun eliminar(colaboracion: Colaboracion) {
        viewModelScope.launch {
            repository.eliminar(colaboracion)
        }
    }
}