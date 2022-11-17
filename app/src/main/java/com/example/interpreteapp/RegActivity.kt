package com.example.interpreteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_reg.*

class RegActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        setup()
        val btnToLogin = findViewById<Button>(R.id.btnToLogin);
        btnToLogin.setOnClickListener{
            startActivity(Intent(this, AuthActivity::class.java))
        }
    }
    private fun setup() {
        title = "REGISTRO"
        btnSingUp.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                if (passwordEditText.text.toString()==passwordEditText2.text.toString()) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                            emailEditText.text.toString(),
                            passwordEditText.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            } else {
                                showAlertSignUp()
                            }
                        }
                } else {
                    showAlertPassword()
                }
            }
        }
    }
    private fun showAlertSignUp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Datos no validos, revise el correo y caracteres para la contraseña")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showAlertPassword() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña no es igual")
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