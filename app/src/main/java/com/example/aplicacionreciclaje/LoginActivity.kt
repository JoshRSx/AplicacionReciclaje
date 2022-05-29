package com.example.aplicacionreciclaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var txtCorreoLog: EditText
    lateinit var txtPassLog: EditText
    lateinit var irReg: TextView
    lateinit var btnLogin:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //===BotonTxt cambiar a interfaz registro
        txtIniciarSesion.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth

        txtCorreoLog = findViewById(R.id.txtCorreoLogin)
        txtPassLog = findViewById(R.id.txtPassLogin)
        btnLogin = findViewById(R.id.btnInicioSesion)


        var txtCorreo: String
        var txtPass: String




        btnLogin.setOnClickListener {

            txtCorreo = txtCorreoLog.text.toString()
            txtPass = txtPassLog.text.toString()

            if (txtCorreo.isNotEmpty() && txtPass.isNotEmpty()) {

                //auth = FirebaseAuthentication
                //Verificar si el usuario esta en la base de datos
                auth.signInWithEmailAndPassword(txtCorreo, txtPass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "Bienvenido!")
                            val user = auth.currentUser   //Obtenemos los datos del usuario
                            updateUI(user)   //Si los datos son correctos se pasa a la siguiente actividad
                            showHome(
                                txtCorreo,
                                MainActivity.ProviderType.BASIC
                            )  //Datos del usuario al navigarion drawer
                        } else {
                            Log.w(ContentValues.TAG, "Error: ", task.exception)
                            Toast.makeText(
                                baseContext, "Vuelva a colocar sus datos",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }
                    }
            } else {

                Toast.makeText(this, "Ingrese su usuario y/o contraseña", Toast.LENGTH_SHORT).show()
            }
        }

    }
        private fun showHome(
            email: String,
            provider: MainActivity.ProviderType
        ) {    //providerType -> Datos al fragment
            val InicioIntent = Intent(this, MainActivity::class.java).apply {

                putExtra("email", email)
                putExtra("provider", provider.name)

            }
            startActivity(InicioIntent)

        }

        fun updateUI(account: FirebaseUser?) {
            if (account != null) {
                startActivity(Intent(this, MainActivity::class.java))

            }
        }
    }
