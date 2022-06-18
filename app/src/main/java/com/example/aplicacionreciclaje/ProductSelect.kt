package com.example.aplicacionreciclaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.aplicacionreciclaje.Adapters.ItemProd
import com.example.aplicacionreciclaje.Adapters.ListaProd
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_product_select.*
import kotlin.properties.Delegates


class ProductSelect : AppCompatActivity() {

    var prod = ListaProd()
    var prodimg =ListaProd()

    lateinit var txtnom :TextView
    lateinit var txtdesc :TextView
    lateinit var txtpuntos :TextView
    lateinit var txtprecioreg :TextView
    lateinit var txtpreciodesc :TextView
    lateinit var imgprueba : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_select)

        val urlpic = "https://peru-quiosco.000webhostapp.com/fotos/"

        prod = intent.getSerializableExtra("str") as ListaProd


        txtnom = findViewById(R.id.nomProdSelect)
        txtdesc = findViewById(R.id.descrProdSelect)
        txtpreciodesc = findViewById(R.id.precDescProdSelect)
        txtprecioreg = findViewById(R.id.precRegProdSelect)
        txtpuntos = findViewById(R.id.puntosProdSelect)
        imgprueba = findViewById(R.id.imgProdSelect)


        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    puntosProdActuales.text = snapshot.child("puntos").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {}


            })
        val ahorro = prod.prereg - prod.predesc
        txtnom.text = prod.nom
        txtdesc.text = prod.desc
        txtpreciodesc.text = "Precio Desc.: S/." + prod.predesc.toString()
        txtprecioreg.text = "Precio Reg.: S/" + prod.prereg.toString()
        txtpuntos.text = prod.puntos.toString()
        ahorroProdSelect.text = "Ahorro de S/" + ahorro

        Glide.with(this) //Picasso.with(contexto)
            .load(urlpic + prod.cod + ".jpg") //  .asBitmap()
            .error(R.drawable.house48px)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imgprueba)


        btnAgregarCarrito.setOnClickListener {

            FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var puntosUser = snapshot.child("puntos").value.toString().toInt()
                        var itemsCarritoUser = snapshot.child("itemsCarrito").value.toString().toInt()


                        //Restar puntos tras agregar productos al carrito
                        val userPuntos =
                            FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                                .child("puntos")


                        val userItemCarrito = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                            .child("itemsCarrito")

                        userItemCarrito.setValue(itemsCarritoUser+1)
                        userPuntos.setValue(puntosUser - prod.puntos)
                    }

                    override fun onCancelled(error: DatabaseError) {}


                })


            Toast.makeText(this, "${prod.nom} agregado!", Toast.LENGTH_SHORT ).show()
            var it: Intent = Intent(this, MainActivity::class.java)
            startActivity(it)

        }


    }
}


