package com.example.aplicacionreciclaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.aplicacionreciclaje.R.id.btnGoogle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {


    private val GOOGLE_SIGN_IN = 100

    private lateinit var auth: FirebaseAuth
    lateinit var txtCorreoLog: EditText
    lateinit var txtPassLog: EditText
    lateinit var irReg: TextView
    lateinit var btnLogin: Button
    lateinit var btnG: Button


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
        btnG = findViewById(R.id.btnGoogle)


        var txtCorreo: String
        var txtPass: String


        //Btn Google Login
        btnG.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build()



            val googleClient = GoogleSignIn.getClient(this, googleConf)
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

        }

        //


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {


            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {


                            if (it.isSuccessful) {

                                val ph = FirebaseAuth.getInstance().currentUser?.photoUrl


                                var it1: Intent = Intent(this, MainActivity::class.java)
                                it1.putExtra("uri",ph)


                                showHome(account.email ?: "", MainActivity.ProviderType.GOOGLE)
                                Toast.makeText(this,"Hola: "+ph, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()

                            }

                        }
                }

            } catch (e: ApiException){
              showAlert()
            }
        }

    }

    fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}