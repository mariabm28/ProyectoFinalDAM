package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class LoginActivity : AppCompatActivity() {

    // Referencias a las vistas del layout
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var crearCuentaButton: Button
    private lateinit var authApi: AuthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenSplash= installSplashScreen()
        Thread.sleep(500)
        screenSplash.setKeepOnScreenCondition{ false }
        setContentView(R.layout.activity_login) // Conecta el layout XML

        // Inicializa los elementos visuales
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        crearCuentaButton = findViewById(R.id.registerButton)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Configura Retrofit para conectar con tu backend
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/") // IP del host local en emulador
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crea la instancia de la API
        authApi = retrofit.create(AuthApi::class.java)

        // Botón Login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario = Usuario(0, "", email, password)

            authApi.login(usuario).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuarioLogueado = response.body()
                        Toast.makeText(this@LoginActivity, "Bienvenido ${usuarioLogueado?.nombre}", Toast.LENGTH_LONG).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("usuario", usuarioLogueado)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Botón Crear Cuenta
        crearCuentaButton.setOnClickListener {
            val intent = Intent(this, CrearUsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}
