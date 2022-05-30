package com.example.aplicacionreciclaje.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionreciclaje.Products
import com.example.aplicacionreciclaje.R

class AdpProd (

    private val mlis: ArrayList<ItemProd>,
    private val itemClick: Products

    ): RecyclerView.Adapter<AdpProd.ViewHolder>() {
        class ViewHolder(item: View): RecyclerView.ViewHolder(item) {

            //para programar eventos en la seleccion
            interface  onArteOnClick{

                fun onItemClickOfe(itProd: ItemProd)


            }

            //crear var de programa para c/control de la vista


            val img = item.findViewById<ImageView>(R.id.img_prod)
            val nombre = item.findViewById<TextView>(R.id.nombre_prod)
            val puntos =  item.findViewById<TextView>(R.id.puntos_prod)
            val descripcion =  item.findViewById<TextView>(R.id.descripcion_prod)
            val precioReg =  item.findViewById<TextView>(R.id.precioReg_prod)
            val precioDesc =  item.findViewById<TextView>(R.id.precioDesc_prod)



        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val vista = LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)
            return ViewHolder(vista)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val itProd: ItemProd = mlis.get(position)
            holder.nombre.setText(itProd.nomProd)
            holder.puntos.setText(""+itProd.puntosProd)
            holder.img.setImageResource(itProd.imgProd)
            holder.descripcion.setText(itProd.descripcionProd)
            holder.precioReg.setText("Precio Reg. S/."+itProd.precioRegProd)
            holder.precioDesc.setText("Precio Dscto. S/."+itProd.precioDescProd)

            holder.itemView.setOnClickListener {
                itemClick.onItemClickOfe(itProd)
            }


        }




        override fun getItemCount(): Int {
            return mlis.size
        }


}
