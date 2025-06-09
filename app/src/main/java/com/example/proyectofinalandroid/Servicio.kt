package com.example.proyectofinalandroid
import java.io.Serializable

data class Servicio(
    val nombre: String,
    val precio: Double? = null,
    val tiempo: Int? = null
)  : Serializable
