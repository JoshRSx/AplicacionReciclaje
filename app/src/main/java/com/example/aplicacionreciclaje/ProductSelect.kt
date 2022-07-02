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
import kotlin.math.roundToInt
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
                        var itemsCarritoUser =
                            snapshot.child("itemsCarrito").value.toString().toInt()
                        var prod1 = snapshot.child("producto1").value.toString()
                        var prod2 = snapshot.child("producto2").value.toString()
                        var prod3 = snapshot.child("producto3").value.toString()

                        //Restar puntos tras agregar productos al carrito
                        val userPuntos =
                            FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                                .child("puntos")
                        val userItemCarrito =
                            FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                                .child("itemsCarrito")



                        val instReferencia = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                            .child("itemsCola")

                        val userCodeProduct1 = instReferencia.child("prod1").child("cod")
                        val userCodeProduct2 = instReferencia.child("prod2").child("cod")
                        val userCodeProduct3 = instReferencia.child("prod3").child("cod")


                        val userNameProduct1 = instReferencia.child("prod1").child("nombre")
                        val userNameProduct2 = instReferencia.child("prod2").child("nombre")
                        val userNameProduct3 = instReferencia.child("prod3").child("nombre")

                        val userPointsProduct1 = instReferencia.child("prod1").child("puntos")
                        val userPointsProduct2 = instReferencia.child("prod2").child("puntos")
                        val userPointsProduct3 = instReferencia.child("prod3").child("puntos")


                        val userPrecDescProduct1 = instReferencia.child("prod1").child("precioDesc")
                        val userPrecDescProduct2 = instReferencia.child("prod2").child("precioDesc")
                        val userPrecDescProduct3 = instReferencia.child("prod3").child("precioDesc")

                        val userAhorroProduct1 = instReferencia.child("prod1").child("ahorro (soles)")
                        val userAhorroProduct2 = instReferencia.child("prod2").child("ahorro (soles)")
                        val userAhorroProduct3 = instReferencia.child("prod3").child("ahorro (soles)")



                        //Agregar +1 item al carrito
                        userItemCarrito.setValue(itemsCarritoUser+1)
                        //Restar puntos según el producto canjeado
                        userPuntos.setValue(puntosUser - prod.puntos)


                        //El usuario elegira máximo 3 productos a canjear
                        var cod = prod.cod
                        var producto = prod.nom
                        var puntos = prod.puntos
                        var precDesc = prod.predesc
                        var aho = prod.prereg-prod.predesc

                        if(itemsCarritoUser==0){
                            userCodeProduct1.setValue(cod)
                            userNameProduct1.setValue(producto)



                            userPointsProduct1.setValue(puntos)
                            userPrecDescProduct1.setValue(precDesc)
                            userAhorroProduct1.setValue(  (aho * 100.0).roundToInt() / 100.0)


                        }
                        if(itemsCarritoUser==1){
                            userCodeProduct2.setValue(cod)
                            userNameProduct2.setValue(producto)
                            userPointsProduct2.setValue(puntos)
                            userPrecDescProduct2.setValue(precDesc)
                            userAhorroProduct2.setValue(  (aho * 100.0).roundToInt() / 100.0)
                        }
                        if(itemsCarritoUser==2){
                            userCodeProduct3.setValue(cod)
                            userNameProduct3.setValue(producto)
                            userPointsProduct3.setValue(puntos)
                            userPrecDescProduct3.setValue(precDesc)
                            userAhorroProduct3.setValue(  (aho * 100.0).roundToInt() / 100.0)
                        }

                        //Agregar producto en la cola
                       // userProduct1.setValue(prod.nom)

                    }

                    override fun onCancelled(error: DatabaseError) {}
                })

            Toast.makeText(this, "${prod.nom} agregado!", Toast.LENGTH_SHORT ).show()
            var it: Intent = Intent(this, MainActivity::class.java)
            startActivity(it)

        }


    }
}


