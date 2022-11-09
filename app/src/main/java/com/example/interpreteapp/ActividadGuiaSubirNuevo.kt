package com.example.interpreteapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.interpreteapp.databinding.ActivityActividadGuiaSubirNuevoBinding
import com.google.firebase.firestore.FirebaseFirestore


class ActividadGuiaSubirNuevo : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    private lateinit var binding : ActivityActividadGuiaSubirNuevoBinding
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_guia_subir_nuevo)
        binding = ActivityActividadGuiaSubirNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener {
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }


        //agregardatos()

        binding.guardar.setOnClickListener{
            val user = hashMapOf(
                "first" to binding.dato1.text.toString(),
                "last" to binding.dato2.text.toString()
            )
            //manera 1
            db.collection("usuarios").document("nombres")
                .set(user)
                .addOnSuccessListener { Log.d("TAG", "Se guardo correctamente 3") }
                .addOnFailureListener { e -> Log.w("TAG", "error $e") }
        }




    }

    private fun agregardatos() {
        val user = hashMapOf(
            "first" to "miguel",
            "last" to "sanchez",
            "born" to 1989
        )
        //manera 1
        db.collection("usuarios")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", documentReference.id)
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "error $e")
            }
        //manera 2
        db.collection("usuarios").document()
            .set(user)
            .addOnSuccessListener { Log.d("TAG", "Se guardo correctamente 2") }
            .addOnFailureListener { e -> Log.w("TAG", "error $e") }
        //manera 3
        db.collection("usuarios").document("nomdoc")
            .set(user)
            .addOnSuccessListener { Log.d("TAG", "Se guardo correctamente 3") }
            .addOnFailureListener { e -> Log.w("TAG", "error $e") }
    }
}