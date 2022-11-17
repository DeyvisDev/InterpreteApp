package com.example.interpreteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_actividad_menu.*

enum class ProviderType {
    BASIC
}
class ActividadMenu : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_menu)

        //creamos una variable para referenciar al boton en diseño
        val btnInterprete = findViewById<Button>(R.id.btnInterprete);
        //activamos la funcion cuando se genere un clink en el boton referenciado
        btnInterprete.setOnClickListener{
            //Cambiamos de vista o actividad de esta a las mencionada despues
            startActivity(Intent(this, ActividadInterprete::class.java))
        }
        val btnTextoSena = findViewById<Button>(R.id.btnTextoSena);
        btnTextoSena.setOnClickListener{
            startActivity(Intent(this, ActividadTextoSena::class.java))
        }
        val btnGuia = findViewById<Button>(R.id.btnGuia);
        btnGuia.setOnClickListener{
            startActivity(Intent(this, ActividadGuia::class.java))
        }
        val btnDiccionario = findViewById<Button>(R.id.btnDiccionario);
        btnDiccionario.setOnClickListener{
            startActivity(Intent(this, ActividadDiccionario::class.java))
        }
        val btnPruebas = findViewById<Button>(R.id.btnPruebas);
        btnPruebas.setOnClickListener{
            startActivity(Intent(this, PruebasAct::class.java))
        }
        val btnPruebasRecycler = findViewById<Button>(R.id.btnPruebasRecycler);
        btnPruebasRecycler.setOnClickListener{
            startActivity(Intent(this, PruebasRecycler::class.java))
        }


        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?:"", provider ?: "")
    }
    private fun setup(email:String, provider:String){
        title= "Inicio"
        emailTextView.text = email
        providerTextView.text = provider

        btnLogOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            //onBackPressed()
        }    }
}