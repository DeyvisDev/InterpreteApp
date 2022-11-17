package com.example.interpreteapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.interpreteapp.databinding.ActivityActividadGuiaSubirNuevoBinding
import com.example.interpreteapp.databinding.ActivityPruebasActBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class PruebasAct : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    private lateinit var binding : ActivityPruebasActBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas_act)
        //configuracion ecencial para guardar en firebase
        binding = ActivityPruebasActBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Boton atras, regresa una activity
        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener {
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
        //leer datos
        //leer()
        //leerVariosDatos()
        //leerVariosDocumentos()

        binding.buscarDato.setOnClickListener {
            val docRef = db.collection("pruebas").document(binding.buscarDatoET.text.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if(document !=null){
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                        Log.d("TAG", "${document.get("first") as String}")
                    } else {
                        Log.d("TAG", "No se encontro el documento")
                    }
                }
                .addOnFailureListener{ exception ->
                    Log.d("TAG", "No se encontro $exception")
                }
        }

        //eliminar datos de firebase
        binding.eliminarDato.setOnClickListener {
            //borra un documenton en este caso pruebas
            /*db.collection("pruebas").document(binding.buscarDatoET.toString())
                .delete()
                .addOnSuccessListener { Log.d("TAG", "Documento borrado") }
                .addOnFailureListener {e -> Log.w("TAG", "Error al borrar documento $e")}*/
            val docRef = db.collection("pruebas").document(binding.buscarDatoET.text.toString())
            val borradato = hashMapOf<String, Any>(
                "first" to FieldValue.delete()
            )
            docRef.update(borradato).addOnCompleteListener{Log.d("TAG", "campo eliminado")}
        }



        //funcion que agrega datos de 3 formas
        //agregardatos()
        //GUARDAR DATOS, experamos el click en el boton guradar, recoge los datos de los edittext para gurdarlos en firebase
        binding.guardar.setOnClickListener{
            val user = hashMapOf(
                "first" to binding.dato1.text.toString(),
                "last" to binding.dato2.text.toString()
            )
            //manera 1
            db.collection("pruebas").document("datos2")
                .set(user)
                .addOnSuccessListener { Log.d("TAG", "Se guardo correctamente") }
                .addOnFailureListener { e -> Log.w("TAG", "error $e") }
        }

        //arrays en documentos, actualizar eliminar agregar
        binding.actualizarDato.setOnClickListener {
            val a = db.collection("pruebas").document(binding.buscarDatoET.text.toString())
        }
        binding.agregarArray.setOnClickListener {  }
        binding.eliminarArray.setOnClickListener {  }
    }

    private fun leer() {
        //obtener un solo documento de firebase
        val docRef = db.collection("pruebas").document("nombres")
        docRef.get()
            .addOnSuccessListener { document ->
                if(document !=null){
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    Log.d("TAG", "${document.get("first") as String}")
                } else {
                    Log.d("TAG", "No se encontro el documento")
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("TAG", "No se encontro $exception")
            }
    }
    private fun leerVariosDatos() {
        //obtener un solo documento de firebase
        val docRef = db.collection("pruebas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id}=>${document.data}")
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("TAG", "Error el encontrar datos $exception")
            }
    }
    private fun leerVariosDocumentos() {
        //obtener un solo documento de firebase
        val docRef = db.collection("pruebas")
            .whereEqualTo("booleanExample", true)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id}=>${document.data}")
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("TAG", "Error el encontrar datos $exception")
            }
    }

    //guardar datos
    private fun agregardatos() {
        val user = hashMapOf(
            "dato1" to "miguel",//string
            "dato2" to binding.dato2.text.toString(),//edit text en string
            "dato3" to true,//bolean
            "dato4" to 3.14153223,//decimal
            "dato5" to arrayListOf(1,2,3,4),//array
            "dato6" to 1989, //numero
            "dato7" to null //dato nulo
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