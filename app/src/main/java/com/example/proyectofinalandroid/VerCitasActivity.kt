package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VerCitasActivity : AppCompatActivity() {

    private lateinit var citasRecyclerView: RecyclerView
    private lateinit var citasAdapter: CitaAdapter
    private lateinit var authApi: AuthApi
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        citasRecyclerView = findViewById(R.id.recyclerViewCitas)
        citasRecyclerView.layoutManager = LinearLayoutManager(this)

        usuario = intent.getSerializableExtra("usuario") as Usuario

        citasAdapter = CitaAdapter(
            emptyList(),
            onEditar = { cita -> editarCita(cita) },
            onEliminar = { cita -> eliminarCita(cita) }
        )
        citasRecyclerView.adapter = citasAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)

        cargarCitasDeUsuario()
    }

    private fun cargarCitasDeUsuario() {
        authApi.obtenerCitasPorUsuario(usuario.id).enqueue(object : Callback<List<Cita>> {
            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                if (response.isSuccessful) {
                    val citas = response.body() ?: emptyList()
                    citasAdapter.actualizarCitas(citas)
                } else {
                    Toast.makeText(this@VerCitasActivity, "Error al cargar citas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Toast.makeText(this@VerCitasActivity, "Fallo de red: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun editarCita(cita: Cita) {
        val intent = Intent(this, AgendarCitaActivity::class.java)
        intent.putExtra("usuario", usuario)
        intent.putExtra("cita", cita)
        startActivity(intent)

    }

    private fun eliminarCita(cita: Cita) {
        authApi.eliminarCita(cita.id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@VerCitasActivity, "Cita eliminada", Toast.LENGTH_SHORT).show()
                    cargarCitasDeUsuario()
                } else {
                    Toast.makeText(this@VerCitasActivity, "Error al eliminar cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@VerCitasActivity, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

}
