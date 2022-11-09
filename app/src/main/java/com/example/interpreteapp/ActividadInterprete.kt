package com.example.interpreteapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class ActividadInterprete : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_dos)

        val btnTomarFoto = findViewById<Button>(R.id.btnTomarFoto)
        btnTomarFoto.setOnClickListener{
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }


        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener{
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
    }
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(imageBitmap)

        }
    }
}