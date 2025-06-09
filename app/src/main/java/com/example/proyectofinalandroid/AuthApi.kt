package com.example.proyectofinalandroid

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Interfaz que define las peticiones HTTP que haremos con Retrofit
interface AuthApi {
    // Llama al endpoint POST /api/auth/login enviando un objeto Usuario
    @POST("auth/login")
    fun login(@Body usuario: Usuario): Call<Usuario>

    // Llama al endpoint POST /api/usuarios para registrar un nuevo usuario
    @POST("usuarios")
    fun crearUsuario(@Body nuevoUsuario: Usuario): Call<Usuario>

    // Llama al endpoint PUT /api/usuarios/{id} enviando un objeto Usuario
    @PUT("usuarios/{id}")
    fun actualizarUsuario(@Path("id") id: Long?, @Body usuario: Usuario): Call<Usuario>

    // Llama al endpoint GET /api/servicios
    @GET("servicios")
    fun obtenerServicios(): Call<List<Servicio>>

    // Llama al endpoint POST /api/citas
    @POST("citas")
    fun agendarCita(@Body cita: Cita): Call<Cita>

    // Llama al endpoint GET /api/usuarios/{id}
    @GET("usuarios/{id}")
    fun obtenerUsuarioPorId(@Path("id") id: Long?): Call<Usuario>

    @GET("citas/usuario/{id}")
    fun obtenerCitasPorUsuario(@Path("id") idUsuario: Long?): Call<List<Cita>>

    @DELETE("citas/{id}")
    fun eliminarCita(@Path("id") id: Int?): Call<Void>

    @PUT("citas/{id}")
    fun editarCita(@Path("id") id: Int, @Body cita: Cita): Call<Cita>
}