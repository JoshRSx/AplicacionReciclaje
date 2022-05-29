package com.example.aplicacionreciclaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    //Creación de variables
    lateinit var btnReg: Button
    lateinit var nom: EditText
    lateinit var apellido: EditText
    lateinit var correo: EditText
    lateinit var pass: EditText





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //===BotonTxt cambiar a interfaz Login
        txtIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        //Definimos variables
        btnReg = findViewById(R.id.btnInicioSesion)
        nom = findViewById(R.id.txtNombre)
        pass = findViewById(R.id.txtPassLogin)
        correo = findViewById(R.id.txtCorreoLogin)
        apellido = findViewById(R.id.txtApellido)



        var txtNom: String
        var txtApellido: String
        var txtCorreo: String
        var txtPass: String

        //==== Firebase

        btnReg.setOnClickListener {

            txtNom = nom.text.toString()
            txtApellido = apellido.text.toString()
            txtCorreo = correo.text.toString()
            txtPass = pass.text.toString()


            if (txtNom.isNotEmpty() && txtApellido.isNotEmpty() && txtCorreo.isNotEmpty() && txtPass.isNotEmpty()  ) {
                if (txtPass.length >= 6) {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtCorreo, txtPass)
                        .addOnCompleteListener { task ->

                            val map = HashMap<String, String>()    //creacion de atributos con los datos del usuario
                            map["nombre"] = txtNom
                            map["apellido"] = txtApellido
                            map["correo"] = txtCorreo
                            map["pass"] = txtPass
                            map["puntos"] = "0"


                            //Identificador de usuario
                            val keys: String = FirebaseDatabase.getInstance().reference.child(FirebaseAuth.getInstance().currentUser!!.uid).key.toString()
                            if (task.isSuccessful) {
                                FirebaseDatabase.getInstance().getReference("Usuarios").child(keys)  //Punto de referencia para id's de los usuario
                                    .setValue(map)        //Ingreso del hashMap  a  firebase
                                    .addOnCompleteListener { task2 ->
                                        if (task2.isSuccessful) {
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            Toast.makeText(this, "Registrado!", Toast.LENGTH_LONG).show()
                                            Firebase.auth.currentUser?.sendEmailVerification()   //Manda mensaje de verificacion al correo registrado
                                            //                            finish()


                                        } else {

                                            Toast.makeText(this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show() }
                                    }
                            } else {

                                Toast.makeText(this, "No se puede registrar a este usuario", Toast.LENGTH_SHORT).show() }
                        }
                } else {

                    Toast.makeText(this, "La contraseña debe tener más de 6 carácteres", Toast.LENGTH_SHORT).show() }
            } else {

                Toast.makeText(this, "Debe de completar todos los campos!", Toast.LENGTH_SHORT).show() }
        }


    }

}