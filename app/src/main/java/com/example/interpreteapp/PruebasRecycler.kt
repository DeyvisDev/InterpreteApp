package com.example.interpreteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.interpreteapp.databinding.ActivityPruebasRecyclerBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class PruebasRecycler : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    private lateinit var adapterTextoSena: AdapterTextoSena
    private lateinit var textosenaList:ArrayList<ItemTextoSena>
    private lateinit var binding: ActivityPruebasRecyclerBinding
    //lateinit var itemTextoSena: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas_recycler)

        binding = ActivityPruebasRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAtras = findViewById<Button>(R.id.btnAtras);
        btnAtras.setOnClickListener {
            //startActivity(Intent(this, ActividadMenu::class.java))
            onBackPressed()
        }
        llamarrecyclerview()
    }
    private fun llamarrecyclerview(){
        textosenaList = ArrayList()

        var adapter = AdapterTextoSena(textosenaList)
        adapterTextoSena = adapter
        adapter.setOnItemClickListener(object : AdapterTextoSena.onItemClickListener{
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@PruebasRecycler, "clicaste en el item: $position",Toast.LENGTH_SHORT).show()
                Log.d("Tag", "gggagag")

                val intent = Intent(this@PruebasRecycler, HomeActivity::class.java )
                startActivity(intent)
            }

        })

        db.collection("TextoSena")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val wallItem = document.toObject(ItemTextoSena::class.java)
                    wallItem.idTextoSena = document.id
                    wallItem.Tex = document["SignificadoTexto"].toString()
                    wallItem.Descr = document["SignificadoDescripcion"].toString()
                    wallItem.Img = document["SignificadoImagen"].toString()
                    binding.recuclerID.adapter = adapterTextoSena
                    binding.recuclerID.layoutManager = LinearLayoutManager(this)
                    textosenaList.add(wallItem)
                }
            }
    }
}