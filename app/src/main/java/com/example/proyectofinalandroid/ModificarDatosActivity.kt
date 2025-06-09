package com.example.proyectofinalandroid

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModificarDatosActivity : AppCompatActivity() {
    private lateinit var editarNombre: EditText
    private lateinit var editarEmail: EditText
    private lateinit var editarPassword: EditText
    private lateinit var btnGuardarCambios: Button
    private lateinit var authApi: AuthApi
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_datos)

        editarNombre = findViewById(R.id.editarNombre)
        editarEmail = findViewById(R.id.editarEmail)
        editarPassword = findViewById(R.id.editarPassword)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authApi = retrofit.create(AuthApi::class.java)

        // Recuperar usuario del intent
        val usuarioIntent = intent.getSerializableExtra("usuario") as Usuario
        usuario = usuarioIntent

        // Llamada al backend para obtener usuario actualizado
        authApi.obtenerUsuarioPorId(usuario.id).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    usuario = response.body()!!
                    editarNombre.setText(usuario.nombre)
                    editarEmail.setText(usuario.email)
                    editarPassword.setText(usuario.password)
                } else {
                    Toast.makeText(this@ModificarDatosActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@ModificarDatosActivity, "Error de red al cargar datos", Toast.LENGTH_LONG).show()
            }
        })

        btnGuardarCambios.setOnClickListener {
            val nuevoNombre = editarNombre.text.toString()
            val nuevoEmail = editarEmail.text.toString()
            val nuevaPassword = editarPassword.text.toString()

            val usuarioActualizado = Usuario(usuario.id, nuevoNombre, nuevoEmail, nuevaPassword)

            authApi.actualizarUsuario(usuario.id, usuarioActualizado).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuarioModificado = response.body()!!
                        Toast.makeText(this@ModificarDatosActivity, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                        intent.putExtra("usuario", usuarioModificado)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("MODIFICAR_DATOS", "Error al actualizar: $errorBody")
                        Toast.makeText(this@ModificarDatosActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Log.e("MODIFICAR_DATOS", "Error de red", t)
                    Toast.makeText(this@ModificarDatosActivity, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
