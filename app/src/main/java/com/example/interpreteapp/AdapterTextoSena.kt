package com.example.interpreteapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class AdapterTextoSena (private var items: List<ItemTextoSena>):
    RecyclerView.Adapter<AdapterTextoSena.ViewHolder>(){
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position : Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterTextoSena.ViewHolder {
        return AdapterTextoSena.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.itemtextosena,parent,false),mListener
        )
    }
    override fun onBindViewHolder(holder: AdapterTextoSena.ViewHolder, position: Int) {
        val item = items[position]
        holder.textom.text = item.Tex
        holder.descm.text = item.Descr
        Glide.with(holder.itemView.context).load(item.Img).circleCrop().into(holder.fotm)
        holder.botm.setOnClickListener {
            val activity = it.context as AppCompatActivity
            Toast.makeText(activity, "ollo, soy ${item.Tex} ${item.Descr}", Toast.LENGTH_LONG).show()
            println("ollo, soy ${item.Tex} ${item.Descr}")

        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view)
    {
        val textom: TextView = view.findViewById(R.id.textoo)
        val descm: TextView = view.findViewById(R.id.desco)
        val fotm: ImageView = view.findViewById(R.id.lafoto)
        val botm: Button = view.findViewById(R.id.botono)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }
    }
}