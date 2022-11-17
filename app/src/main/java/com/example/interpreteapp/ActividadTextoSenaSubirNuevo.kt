package com.example.interpreteapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.interpreteapp.databinding.ActivityActividadTextoSenaSubirNuevoBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.HashMap
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class ActividadTextoSenaSubirNuevo : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private val File = 1
    private val database = Firebase.database
    var uriImg = String



    private lateinit var binding : ActivityActividadTextoSenaSubirNuevoBinding
    val myRef = database.getReference("user")
    val db = FirebaseFirestore.getInstance()

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_texto_sena_subir_nuevo)

        binding = ActivityActividadTextoSenaSubirNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener{
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
        val URIImg = binding.btnGaleria.setOnClickListener { requestPermissions() }
        Log.d("Ir a galeria En pantalla", URIImg.toString())


        val btnSubirImg = findViewById<Button>(R.id.btnSubirImg);
        btnSubirImg.setOnClickListener{

            FirebaseApp.initializeApp(/*context=*/this)
            val firebaseAppCheck = FirebaseAppCheck.getInstance()
            firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
            Log.d("Mensaje","Enviado token debugg")
            //FirebaseApp.initializeApp(/*context=*/this)
            //val firebaseAppCheck = FirebaseAppCheck.getInstance()
            //firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance())
            fileUpload()
            //startActivity(Intent(this, ActividadTextoSenaSubirNuevo::class.java))
        }



    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickPhotoFromGallery()
                }
                else-> requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            pickPhotoFromGallery()
        }
    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->
        if (isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(this, "You need to enable the permission", Toast.LENGTH_SHORT).show()
        }


    }
    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result->
        if (result.resultCode == Activity.RESULT_OK){

            val data = result.data?.data
            //uriImg = result.data?.data

            Log.d("Ir a galeria link", data.toString())

            binding.imageView.setImageURI(data)

        }

    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type= "image/*"
        startForActivityGallery.launch(intent)
    }

    private fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
        //startActivityForResult(intent, File)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("TextoSenaPrueba")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)

                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        val hashMap = HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        Log.d("Mensaje4", uri.toString())
                        val URLImg = uri.toString()
                        val user = hashMapOf(
                            "SignificadoTexto" to binding.significadoTexto.text.toString(),
                            "SignificadoDescripcion" to binding.significadoDescripcion.text.toString(),
                            "SignificadoImagen" to URLImg
                        )
                        //manera 1
                        db.collection("TextoSena").document()
                            .set(user)
                            .addOnSuccessListener { Log.d("TAG", "Se guardo correctamente 3") }
                            .addOnFailureListener { e -> Log.w("TAG", "error $e") }
                        myRef.setValue(hashMap)
                    }
                }
            }
        }
    }
}