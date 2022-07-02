package com.example.aplicacionreciclaje.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.aplicacionreciclaje.Products
import com.example.aplicacionreciclaje.R

class AdpSectionProd(

    private val mlis: ArrayList<ItemSectionProd>,
    private val contexto: Context?,
    private val itemClick:Products

): RecyclerView.Adapter<AdpSectionProd.ViewHolder>() {

    val urlpicSect="https://peru-quiosco.000webhostapp.com/fotos_seccion/"


    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {

        /*para programar eventos en la seleccion        */
        interface  onArteOnClick{

            fun onItemClickSec(sec: ItemSectionProd)
        }



        //crear var de programa para c/control de la vista

        val nombre = item.findViewById<TextView>(R.id.txtvSeccion)
        val codimg = item.findViewById<ImageView>(R.id.imgvSeccion)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.product_section,parent,false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sec:ItemSectionProd=mlis.get(position)
        holder.nombre.setText(sec.nomSeccion)

        if (contexto != null) {
            Glide.with(contexto) //Picasso.with(contexto)
                .load(urlpicSect + sec.imgSection + ".jpg") //  .asBitmap()
                .error(R.drawable.house48px)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.codimg)
        }


        holder.itemView.setOnClickListener {
            itemClick.onItemClickSec(sec)
        }


    }



    override fun getItemCount(): Int {
        return mlis.size
    }

}
