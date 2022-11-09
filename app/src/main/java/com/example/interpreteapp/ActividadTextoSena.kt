package com.example.interpreteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActividadTextoSena : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_texto_sena)
        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener{
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
        val btnSubir = findViewById<Button>(R.id.btnSubir);
        btnSubir.setOnClickListener{
            startActivity(Intent(this, ActividadTextoSenaSubirNuevo::class.java))
            //onBackPressed()
        }
    }
}