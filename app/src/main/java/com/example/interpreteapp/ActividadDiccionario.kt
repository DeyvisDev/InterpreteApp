package com.example.interpreteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActividadDiccionario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_diccionario)
        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener{
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
    }
}