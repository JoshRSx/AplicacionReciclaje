package com.example.aplicacionreciclaje

import android.R.id.button1
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacionreciclaje.Adapters.AdpProd
import com.example.aplicacionreciclaje.Adapters.ItemProd
import com.example.aplicacionreciclaje.Adapters.ListaProd
import kotlinx.android.synthetic.main.fragment_products.*
import org.json.JSONException


class Products : Fragment() {

    val itemProd: ArrayList<ItemProd> = ArrayList()

    lateinit var producto:EditText
    lateinit var btnBusqueda:Button


    val url = "https://peru-quiosco.000webhostapp.com/Controlarec.php"
    var cola: RequestQueue?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_products, container, false)

        btnBusqueda = v.findViewById(R.id.btnBusqueda)


        val recyProd = v.findViewById<RecyclerView>(R.id.recyProd)
        recyProd.layoutManager = LinearLayoutManager(context)
        recyProd.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //Definimos variables
        producto = v.findViewById(R.id.txtProducto)

        var prod: String = ""
        cola = Volley.newRequestQueue(context)

     //   val adpProd = AdpProd(itemProd, requireContext())



        btnBusqueda.setOnClickListener {

            prod = producto.text.toString()

            llena(prod)
            // llena(prod)

        }

        return v

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    //Conectar con mySQL para devolver los productos de la tabLa
    //=nomprod-puntos-descr-urlimg-precioreg-preciodscto

    fun llena(prod:String){

        val direc = url+"?tag=consultaprod&nom="+prod
        val req = JsonObjectRequest(Request.Method.GET, direc, null, Response.Listener { response ->
            //CAPTURAR LA MATRIZ
            try {

                val vector = response.getJSONArray("dato")

                val lista: ArrayList<ListaProd> = ArrayList()
                for (f in 0 until vector.length()){
                    val fila = vector.getJSONObject(f)

                    var a =ListaProd()

                    //Agarra el valor de los atributos según su ID
                    a.cod=fila.getInt("cod").toInt()
                    a.nom=fila.getString("nom").toString()
                    a.desc=fila.getString("desc").toString()
                    a.puntos=fila.getInt("puntos").toInt()
                    a.prereg=fila.getDouble("prereg").toDouble()
                    a.predesc=fila.getDouble("predesc").toDouble()
            //        a.imgprod = fila.getInt("imgprod").toInt()

                    lista.add(a)


                }

                val dp= AdpProd(lista, this.requireContext(), this)
                recyProd.layoutManager= LinearLayoutManager(this.requireContext())
                recyProd.addItemDecoration(DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL))
                recyProd.adapter=dp

            }catch (ex: JSONException){
                ex.printStackTrace()
            }


        }, Response.ErrorListener { error ->

            Toast.makeText(context , "error $error", Toast.LENGTH_SHORT).show()



        })
        cola?.add(req)

    }



    fun onItemClickProd(p: ListaProd) {

        var it: Intent = Intent(context, ProductSelect::class.java)
        it.putExtra("str",p)


        startActivity(it)
    }


}