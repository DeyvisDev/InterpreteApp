package com.example.interpreteapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapterlista (private var items: List<ItemUsu>):
RecyclerView.Adapter<Adapterlista.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): Adapterlista.ViewHolder {
        return Adapterlista.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.itemusu,parent,false)
        )

    }
    override fun onBindViewHolder(holder: Adapterlista.ViewHolder, position: Int) {
        val item = items[position]
        holder.textom.text = item.Tex
        holder.descm.text = item.Desc
        Glide.with(holder.itemView.context).load(item.Img).circleCrop().into(holder.fotm)
        holder.botm.setOnClickListener {
            val activity = it.context as AppCompatActivity
            Toast.makeText(activity, "ollo, soy ${item.Tex} ${item.Desc}", Toast.LENGTH_LONG).show()
            println("ollo, soy ${item.Tex} ${item.Desc}")
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val textom: TextView = view.findViewById(R.id.textoo)
        val descm: TextView = view.findViewById(R.id.desco)
        val fotm: ImageView = view.findViewById(R.id.lafoto)
        val botm: Button = view.findViewById(R.id.botono)
    }
}