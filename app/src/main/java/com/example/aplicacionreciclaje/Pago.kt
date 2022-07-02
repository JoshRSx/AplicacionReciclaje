package com.example.aplicacionreciclaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_pago.*
import kotlin.math.roundToInt

class Pago : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)



        val urlpic = "https://peru-quiosco.000webhostapp.com/fotos/"

        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    var instChild = snapshot.child("itemsCola")


                    var nomprod1 = instChild.child("prod1").child("nombre").value.toString()
                    var nomprod2 = instChild.child("prod2").child("nombre").value.toString()
                    var nomprod3 = instChild.child("prod3").child("nombre").value.toString()

                    var precdescprod1 = instChild.child("prod1").child("precioDesc").value.toString()
                    var precdescprod2 = instChild.child("prod2").child("precioDesc").value.toString()
                    var precdescprod3 = instChild.child("prod3").child("precioDesc").value.toString()

                    var codprod1 = instChild.child("prod1").child("cod").value.toString()
                    var codprod2 = instChild.child("prod2").child("cod").value.toString()
                    var codprod3 = instChild.child("prod3").child("cod").value.toString()


                    Glide.with(applicationContext) //Picasso.with(contexto)
                        .load(urlpic + codprod1 + ".jpg") //  .asBitmap()
                        .error(R.drawable.house48px)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(imgProdCola1)

                    Glide.with(applicationContext) //Picasso.with(contexto)
                        .load(urlpic + codprod2 + ".jpg") //  .asBitmap()
                        .error(R.drawable.house48px)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(imgProdCola2)

                    Glide.with(applicationContext) //Picasso.with(contexto)
                        .load(urlpic + codprod3 + ".jpg") //  .asBitmap()
                        .error(R.drawable.house48px)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(imgProdCola3)


                    var punprod1 = instChild.child("prod1").child("puntos").value.toString()
                    var punprod2 = instChild.child("prod2").child("puntos").value.toString()
                    var punprod3 = instChild.child("prod3").child("puntos").value.toString()


                    prodpuntosCola1.setText(punprod1.toString())
                    prodpuntosCola2.setText(punprod2.toString())
                    prodpuntosCola3.setText(punprod3.toString())



                    prodCola1.setText(nomprod1)
                    prodCola2.setText(nomprod2)
                    prodCola3.setText(nomprod3)


         var prectot = precdescprod1.toDouble() + precdescprod2.toDouble() + precdescprod3.toDouble()
         var puntostot = punprod1.toInt() + punprod2.toInt() + punprod3.toInt()


                    totalpago.setText("Total S/."+  ((prectot * 100.0).roundToInt() / 100.0).toString())
                    totalpuntos.setText(puntostot.toString())

                    var itemsColaUser = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).child("itemsCola")
                    var puntosUser = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).child("puntos")
                    var itemsNumUser = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).child("itemsCarrito")


                    btnPagar.setOnClickListener {



                        itemsColaUser.setValue("")
                        puntosUser.setValue("0")
                        itemsNumUser.setValue("0")

                        Toast.makeText(applicationContext, "Felicidades por la compra, revise el correo", Toast.LENGTH_SHORT).show()


                        val home: Intent = Intent(this@Pago , MainActivity::class.java)
                        startActivity(home)



                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


})



    }

}

