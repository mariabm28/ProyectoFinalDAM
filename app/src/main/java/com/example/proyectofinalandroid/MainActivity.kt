package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuario = intent.getSerializableExtra("usuario") as Usuario
        findViewById<TextView>(R.id.welcomeTextView).text = "Bienvenido, ${usuario.nombre}"

        findViewById<LinearLayout>(R.id.modificarDatosBtn).setOnClickListener {
            //startActivity(Intent(this, ModificarDatosActivity::class.java))
            val intent = Intent(this, ModificarDatosActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.agendarCitaBtn).setOnClickListener {
            //startActivity(Intent(this, AgendarCitaActivity::class.java))
            val intent = Intent(this, AgendarCitaActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.verCitasBtn).setOnClickListener {
            val intent = Intent(this, VerCitasActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.cerrarSesionBtn).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}

