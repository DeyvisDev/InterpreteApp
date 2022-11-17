package com.example.interpreteapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interpreteapp.databinding.ActivityActividadTextoSenaBinding
import com.example.interpreteapp.databinding.ActivityActividadTextoSenaSubirNuevoBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Objects

class ActividadTextoSena : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    private lateinit var adapterTextoSena: AdapterTextoSena
    private lateinit var textosenaList:ArrayList<ItemTextoSena>
    private lateinit var binding : ActivityActividadTextoSenaBinding


    // Variables para pasar datos en intent
    var idA = arrayListOf<String>()
    var textoA = arrayListOf<String>()
    var descrA = arrayListOf<String>()
    var imgA = arrayListOf<String>()
    var ItemsVer = arrayListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_texto_sena)

        binding = ActivityActividadTextoSenaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtras.setOnClickListener {
            onBackPressed()
        }

        binding.btnSubir.setOnClickListener {
            startActivity(Intent(this, ActividadTextoSenaSubirNuevo::class.java))
        }

        llamarrecyclerview()


        /*val btnAtras = findViewById<Button>(R.id.btnAtras)
        btnAtras.setOnClickListener{
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }*/
        /*val btnSubir = findViewById<Button>(R.id.btnSubir)
        btnSubir.setOnClickListener{
            startActivity(Intent(this, ActividadTextoSenaSubirNuevo::class.java))
            //onBackPressed()
        }*/

        //verrecycler()

    }
    private fun llamarrecyclerview(){
        textosenaList = ArrayList()

        var adapter = AdapterTextoSena(textosenaList)
        adapterTextoSena = adapter
        adapter.setOnItemClickListener(object : AdapterTextoSena.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@PruebasRecycler, "clicaste en el item: $position",Toast.LENGTH_SHORT).show()
                //ItemsVer.toList()
                //Log.d("Tag", ItemsVer[position])

                //Log.d("Tag", ItemsVer.get(position).get(3).toString())
                //ItemsVer.get(position).get(1)

                var intent = Intent(this@ActividadTextoSena, ActividadTextoSenaVerItem::class.java ).apply {
                    putExtra("id",idA[position])
                    putExtra("texto", textoA[position])
                    putExtra("descr", descrA[position])
                    putExtra("img", imgA[position])
                }
                startActivity(intent)
            }

        })

        db.collection("TextoSena")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents){
                    val wallItem = document.toObject(ItemTextoSena::class.java)
                    wallItem.idTextoSena = document.id

                    //llenamos variables para enviar en intent
                    idA.add(document.id)
                    textoA.add(document["SignificadoTexto"].toString())
                    descrA.add(document["SignificadoDescripcion"].toString())
                    imgA.add(document["SignificadoImagen"].toString())
                    //enviamos los datos recividos en un item wall
                    wallItem.Tex = document["SignificadoTexto"].toString()
                    wallItem.Descr = document["SignificadoDescripcion"].toString()
                    wallItem.Img = document["SignificadoImagen"].toString()
                    binding.recuclerID.adapter = adapterTextoSena
                    binding.recuclerID.layoutManager = LinearLayoutManager(this)
                    textosenaList.add(wallItem)
                    //ItemsVer.add(wallItem.toString())

                }

            }

    }
    /*private fun verrecycler(){
        adapterlista = Adapterlista(cargalista())
        binding.recycler.adapter = adapterlista
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }*/


}


