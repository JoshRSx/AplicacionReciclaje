package com.example.aplicacionreciclaje

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionreciclaje.Adapters.AdpProd
import com.example.aplicacionreciclaje.Adapters.ItemProd

class Products : Fragment() {

    val itemProd: ArrayList<ItemProd> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v:View = inflater.inflate(R.layout.fragment_products, container, false)
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyProd = view.findViewById<RecyclerView>(R.id.recyProd)

        recyProd.layoutManager = LinearLayoutManager(context)
        recyProd.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        val adpProd = AdpProd(itemProd, this)
        recyProd?.adapter = adpProd


        llenaItemProductos()

    }



    //Conectar con mySQL para devolver los productos de la tabLa

    //=nomprod-puntos-descr-urlimg-precioreg-preciodscto


    fun llenaItemProductos(){

        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.impresora,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.cooler,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500, "AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.mochila,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.impresora,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.laptop,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.cooler,79.90, 59.90))
        itemProd.add(ItemProd("Impresora Epson LX255G",500,"AAAAB3NzaC1yc2EAAAADAQABAAABAQDVrIGItx/NtjCGncP/OYglIyb7jL",R.drawable.laptop,79.90, 59.90))


    }






    fun onItemClickOfe(itProd: ItemProd) {

    }


}