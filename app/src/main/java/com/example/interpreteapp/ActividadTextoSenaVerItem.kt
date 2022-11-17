package com.example.interpreteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.interpreteapp.databinding.ActivityActividadTextoSenaVerItemBinding

class ActividadTextoSenaVerItem : AppCompatActivity() {
    private lateinit var binding : ActivityActividadTextoSenaVerItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_texto_sena_ver_item)
        //recivimos los datos enviados por intent
        val objetoIntent: Intent=intent
        var textoVer = objetoIntent.getStringExtra("texto")
        var descrVer = objetoIntent.getStringExtra("descr")
        var imgVer = objetoIntent.getStringExtra("img")
        var idVer = objetoIntent.getStringExtra("id")
        //Log.d("TAG","$textoVer $descrVer $idVer $imgVer ")

        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener{
            onBackPressed()
        }
        Glide.with(this)
            .load(imgVer)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.imagenSena))
        val txtTexto = findViewById<TextView>(R.id.txtTexto)
        txtTexto.text = textoVer
        val txtDescripcion= findViewById<TextView>(R.id.txtDescripcion)
        txtDescripcion.text = descrVer


    }
}