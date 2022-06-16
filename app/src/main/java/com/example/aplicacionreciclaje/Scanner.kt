package com.example.aplicacionreciclaje

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplicacionreciclaje.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class Scanner : Fragment() {

    lateinit var btnScan:Button
    lateinit var navigationView : NavigationView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /*
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    */


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_scanner, container, false)

        btnScan = v.findViewById(R.id.btnEscaneo)


        btnScan.setOnClickListener { initScanner() }

        return v
    }

    //Iniciar scanner
    private fun initScanner(){
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        //integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }


    //Recuperar resultado

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this.requireContext(), "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this.requireContext(), "Valor QR: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    //Agregar puntos al usuario según la cantidad de KG

        if(result.contents.equals("https://s.qrfy.mobi/gw4LNBp")){

            Toast.makeText(context, "El valor es reconocido", Toast.LENGTH_SHORT).show()


            val user = FirebaseAuth.getInstance().currentUser
     //       navigationView = view?.findViewById(R.id.nav_view)!!

            //Del firebaseUser.puntos cambiar a +20
            val uid: String = FirebaseAuth.getInstance().currentUser!!.uid
            val userPuntos = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).child("puntos")



            FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val punUsuario: String = snapshot.child("puntos").value.toString()

                    val userPuntosInt: Int = punUsuario.toInt()

                    userPuntos.setValue((userPuntosInt+20).toString())


                    //Get valor puntos

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

        })


        }

}
}