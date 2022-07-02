package com.example.aplicacionreciclaje

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aplicacionreciclaje.Adapters.*
import kotlinx.android.synthetic.main.fragment_products.*
import org.json.JSONException
import kotlin.collections.ArrayList


class Products : Fragment() {

    val listaProd: ArrayList<ItemProd> = ArrayList()   //Listar Productos MySQL
    private val listaSectProd:ArrayList<ItemSectionProd> = ArrayList()       //Listar nombres de seccion de productos

    lateinit var recPr: RecyclerView
    lateinit var recSectPr: RecyclerView

    lateinit var producto:EditText
    lateinit var btnBusqueda:Button



    val url = "https://peru-quiosco.000webhostapp.com/Controlarec.php"
    val direc = "https://peru-quiosco.000webhostapp.com/Controlarec.php?tag=consultaprod&nom="
    val direcTec = "https://peru-quiosco.000webhostapp.com/Controlarec.php?tag=consultatec&nom="
    val direcAcc = "https://peru-quiosco.000webhostapp.com/Controlarec.php?tag=consultaacc&nom="
    val direcOtro = "https://peru-quiosco.000webhostapp.com/Controlarec.php?tag=consultaotr&nom="
    var cola: RequestQueue?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_products, container, false)





        recPr = v.findViewById(R.id.recyProd)
        producto = v.findViewById(R.id.txtProducto)
        cola = Volley.newRequestQueue(context)
        producto.text.toString()


        btnBusqueda = v.findViewById(R.id.btnBusqueda)
        //   val adpProd = AdpProd(itemProd, requireContext())


        var busquedaText: String = ""
        var filtro:String = "prod"

        producto.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                producto.text.toString()
                busquedaText = producto.text.toString()
                llenaProductos(busquedaText, direc)
                true
            } else {
                false
            }
        }


        btnBusqueda.setOnClickListener {

            producto.text.toString()
            busquedaText = producto.text.toString()

            llenaProductos(busquedaText, direc+busquedaText)
            // llena(prod)
        }




        return v

    }

    override fun onResume() {
        super.onResume()
        btnBusqueda.setTextColor(Color.parseColor("#ffffff"));
            btnBusqueda.callOnClick()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }



    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

      recSectPr =v.findViewById(R.id.recySect)


        recSectPr.layoutManager = LinearLayoutManager(context)
        recSectPr.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        // Seccion productos
        val adpSeccion = AdpSectionProd(listaSectProd,context,this)
        recSectPr.adapter = adpSeccion


        llenaSeccion()

    }


    fun llenaProductos(busquedaText : String, direc: String ){


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

                val dp= AdpProd(lista, this.context, this)
                recPr.layoutManager= LinearLayoutManager(this.context)
              //  recPr.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                recPr.adapter=dp



            }catch (ex: JSONException){
                ex.printStackTrace()
            }


        }, Response.ErrorListener { error ->

            Toast.makeText(context , "error $error", Toast.LENGTH_SHORT).show()



        })
        cola?.add(req)

    }

    fun llenaSeccion() {

     var sec1= listaSectProd.add(ItemSectionProd("Tecnología",1))
     var sec2=   listaSectProd.add(ItemSectionProd("Accesorios",2))
     var sec3=   listaSectProd.add(ItemSectionProd("Otros",3))
        //listaSectProd.add(ItemSectionProd("Utensilios"))
       // listaSectProd.add(ItemSectionProd("Ropa"))


    }





    fun onItemClickProd(p: ListaProd) {

        var it: Intent = Intent(context, ProductSelect::class.java)
        it.putExtra("str",p)
        startActivity(it)
    }

    fun onItemClickSec(sec: ItemSectionProd) {

        recySect.findViewHolderForAdapterPosition(0)?.itemView?.setOnClickListener {

           llenaProductos("",direcTec)

        }
        recySect.findViewHolderForAdapterPosition(1)?.itemView?.setOnClickListener {

            llenaProductos("",direcAcc)

        }

        recySect.findViewHolderForAdapterPosition(2)?.itemView?.setOnClickListener {

            llenaProductos("",direcOtro)

        }

    }


}


