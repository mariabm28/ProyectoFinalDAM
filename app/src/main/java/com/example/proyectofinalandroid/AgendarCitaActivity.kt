package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class AgendarCitaActivity : AppCompatActivity() {
    private lateinit var spinnerServicios: Spinner
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var btnAgendar: Button

    private lateinit var authApi: AuthApi
    private var servicios: List<Servicio> = listOf()
    private lateinit var usuario: Usuario
    private var citaExistente: Cita? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        spinnerServicios = findViewById(R.id.spinnerServicios)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)
        btnAgendar = findViewById(R.id.btnAgendar)

        usuario = intent.getSerializableExtra("usuario") as Usuario
        citaExistente = intent.getSerializableExtra("cita") as? Cita

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)

        cargarServicios()

        btnAgendar.setOnClickListener {
            if (citaExistente != null) {
                editarCita()
            } else {
                agendarCita()
            }
        }
    }

    private fun cargarServicios() {
        authApi.obtenerServicios().enqueue(object : Callback<List<Servicio>> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<List<Servicio>>, response: Response<List<Servicio>>) {
                if (response.isSuccessful) {
                    servicios = response.body() ?: listOf()
                    val listaservicios = servicios.map { "${it.nombre} - ${it.precio}€ - ${it.tiempo} min" }
                    val adapter = ArrayAdapter(this@AgendarCitaActivity, android.R.layout.simple_spinner_item, listaservicios)

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerServicios.adapter = adapter

                    // Si estamos editando una cita, precargamos los datos
                    citaExistente?.let { cita ->
                        val servicioIndex = servicios.indexOfFirst { it.nombre == cita.servicio.nombre }
                        if (servicioIndex >= 0) spinnerServicios.setSelection(servicioIndex)

                        val fechaHora = LocalDateTime.parse(cita.fecha)
                        datePicker.updateDate(fechaHora.year, fechaHora.monthValue - 1, fechaHora.dayOfMonth)
                        timePicker.hour = fechaHora.hour
                        timePicker.minute = fechaHora.minute

                        btnAgendar.text = "Editar Cita"
                    }
                }
            }

            override fun onFailure(call: Call<List<Servicio>>, t: Throwable) {
                Toast.makeText(this@AgendarCitaActivity, "Error al cargar servicios", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun agendarCita() {
        val fechaHora = obtenerFechaHoraSeleccionada()
        val servicioSeleccionado = servicios[spinnerServicios.selectedItemPosition]
        val nuevaCita = Cita(null, usuario, fechaHora, servicioSeleccionado)

        authApi.agendarCita(nuevaCita).enqueue(object : Callback<Cita> {
            override fun onResponse(call: Call<Cita>, response: Response<Cita>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AgendarCitaActivity, "Cita agendada", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AgendarCitaActivity, "Error al agendar cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cita>, t: Throwable) {
                Log.e("AGENDAR_CITA", "Error de red", t)
                Toast.makeText(this@AgendarCitaActivity, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()

            }


        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editarCita() {
        val fechaHora = obtenerFechaHoraSeleccionada()
        val servicioSeleccionado = servicios[spinnerServicios.selectedItemPosition]
        val citaActualizada = Cita(citaExistente!!.id, usuario, fechaHora, servicioSeleccionado)

        authApi.editarCita(citaActualizada.id!!, citaActualizada).enqueue(object : Callback<Cita> {
            override fun onResponse(call: Call<Cita>, response: Response<Cita>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AgendarCitaActivity, "Cita editada", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AgendarCitaActivity, MainActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@AgendarCitaActivity, "Error al editar cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cita>, t: Throwable) {
                Toast.makeText(this@AgendarCitaActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun obtenerFechaHoraSeleccionada(): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        val hour = timePicker.hour
        val minute = timePicker.minute
        val fechaHora = LocalDateTime.of(year, month, day, hour, minute)
        return fechaHora.toString()
    }
}
