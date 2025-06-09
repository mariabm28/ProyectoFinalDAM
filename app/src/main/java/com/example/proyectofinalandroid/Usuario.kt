package com.example.proyectofinalandroid

import java.io.Serializable

data class Usuario(
    val id: Long? = null,
    val nombre: String = "",
    val email: String,
    val password: String,
    val rol: String = ""
) : Serializable
