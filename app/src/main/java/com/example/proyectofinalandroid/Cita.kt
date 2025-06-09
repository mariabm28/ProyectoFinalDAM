package com.example.proyectofinalandroid
import java.io.Serializable

data class Cita(
    val id: Int? =null,
    val usuario: Usuario,
    val fecha: String, // formato: "2025-06-01T15:30:00"
    val servicio: Servicio
) :Serializable

