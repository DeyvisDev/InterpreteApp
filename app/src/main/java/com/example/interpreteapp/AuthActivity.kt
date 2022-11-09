package com.example.interpreteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import java.security.Provider

class AuthActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        val btnToRegistro = findViewById<Button>(R.id.btnToRegistro);
        btnToRegistro.setOnClickListener{
            startActivity(Intent(this, RegActivity::class.java))
        }

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Message","Integracion con firebase completaa")
        analytics.logEvent("LogActivity", bundle)


        // Setup
        setup()

    }
    private fun setup(){
        title = "Autenticacion"
        btnLogin.setOnClickListener {
            if (EmailEditText.text.isNotEmpty() && PasswordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        EmailEditText.text.toString(),
                        PasswordEditText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en la autenticacion al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val menuIntent = Intent(this, ActividadMenu::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(menuIntent)

    }
}