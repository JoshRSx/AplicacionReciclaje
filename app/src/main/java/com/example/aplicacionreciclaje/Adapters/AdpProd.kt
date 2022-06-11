package com.example.aplicacionreciclaje.Adapters

import android.content.Context
import android.content.Intent
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
class AdpProd (private val mlist: ArrayList<ListaProd>,
               private val contexto: Context
): RecyclerView.Adapter<AdpProd.ViewHolder>(){



    interface onArteOnclick{
        //  fun onItemClick(btnNota: ListaProd);

    }

    // Fotos
    val urlpic="https://peru-quiosco.000webhostapp.com/fotos/"




    class ViewHolder(item : View): RecyclerView.ViewHolder(item) {

        val nom: TextView =item.findViewById(R.id.nombre_prod)
        val desc: TextView =item.findViewById(R.id.descripcion_prod)
        val puntos: TextView =item.findViewById(R.id.puntos_prod)
        val precioreg:TextView =item.findViewById(R.id.precioReg_prod)
        val preciodesc:TextView =item.findViewById(R.id.precioDesc_prod)
        val imgProd:ImageView = item.findViewById(R.id.img_prod)
    }







    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AdpProd.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.product_item,parent,false)






        return AdpProd.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdpProd.ViewHolder, position: Int) {
        val p= mlist.get(position)

        holder.nom.setText(""+p.nom)
        holder.desc.setText(""+p.desc)
        holder.puntos.setText(""+p.puntos)
        holder.precioreg.setText("Precio Reg. S/."+p.prereg)
        holder.preciodesc.setText("Precio Desc. S/."+p.predesc)



        /*    if(p.prom()>=11.5)
                holder.tprom.setTextColor(Color.BLUE)
            else
                holder.tprom.setTextColor(Color.RED)

 */
            Glide.with(contexto) //Picasso.with(contexto)
                .load(urlpic + p.cod + ".jpg") //  .asBitmap()
                .error(R.drawable.house48px)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.imgProd)



    }

    override fun getItemCount(): Int {
        return mlist.size
    }



}
