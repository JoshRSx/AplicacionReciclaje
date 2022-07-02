package com.example.aplicacionreciclaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {

    //Creación de variables
    private val GOOGLE_SIGN_IN = 100
    lateinit var btnReg: Button
    lateinit var nom: EditText
    lateinit var apellido: EditText
    lateinit var correo: EditText
    lateinit var pass: EditText
    lateinit var btnG: Button


//KOTLIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //===BotonTxt cambiar a interfaz Login
        txtIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        //Definimos variables
        btnReg = findViewById(R.id.btnRegistro)
        nom = findViewById(R.id.txtNombre)
        pass = findViewById(R.id.txtPassRegister)
        correo = findViewById(R.id.txtCorreoRegister)
        apellido = findViewById(R.id.txtApellido)
        btnG = findViewById(R.id.btnGoogleReg)


        var txtNom: String
        var txtApellido: String
        var txtCorreo: String
        var txtPass: String



        //==== Registro Normal
        btnReg.setOnClickListener {

            txtNom = nom.text.toString()
            txtApellido = apellido.text.toString()
            txtCorreo = correo.text.toString()
            txtPass = pass.text.toString()


            if (txtNom.isNotEmpty() && txtApellido.isNotEmpty() && txtCorreo.isNotEmpty() && txtPass.isNotEmpty()  ) {

                //Contraseña mayor a 6 carácteres y una letra en mayúscula
                if (txtPass.length >= 6 && txtPass.matches(".*[A-Z].*".toRegex()) && txtPass.matches(".*[a-z].*".toRegex()) ) {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtCorreo, txtPass)
                        .addOnCompleteListener { task ->

                            val map = HashMap<String, String>()    //creacion de atributos con los datos del usuario
                            map["nombre"] = txtNom
                            map["apellido"] = txtApellido
                            map["correo"] = txtCorreo
                            map["pass"] = txtPass
                            map["puntos"] = "0"
                            map["itemsCarrito"] = "0"
                            map["itemsCola"] = ""


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
                                        } else {

                                            Toast.makeText(this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show() }
                                    }
                            } else {

                                Toast.makeText(this, "El correo no es valido", Toast.LENGTH_SHORT).show() }
                        }
                } else {

                    Toast.makeText(this, "La contraseña debe tener más de 6 dígitos y carácteres especiales", Toast.LENGTH_SHORT).show() }
            } else {

                Toast.makeText(this, "Debe de completar todos los campos!", Toast.LENGTH_SHORT).show() }
        }

        //==== Registro Google
        btnG.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)

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
                                //    val ph = FirebaseAuth.getInstance().currentUser?.photoUrl
                                val user = FirebaseAuth.getInstance().currentUser
                                //Codigo registro Google a Firebase
                                val map = HashMap<String, String>()    //creacion de atributos con los datos del usuario
                                map["NombreApellido"] = user?.displayName.toString()
                                map["correo"] = user?.email.toString()
                                map["puntos"] = 750.toString()
                                map["itemsCarrito"] = "0"
                                map["itemsCola"] = ""


                                //Identificador de usuario
                                val keys: String = FirebaseDatabase.getInstance().reference.child(FirebaseAuth.getInstance().currentUser!!.uid).key.toString()
                                if (task.isSuccessful) {
                                    FirebaseDatabase.getInstance().getReference("Usuarios").child(keys)  //Punto de referencia para id's de los usuario
                                        .setValue(map)        //Ingreso del hashMap  a  firebase
                                        .addOnCompleteListener { task2 ->
                                            if (task2.isSuccessful) {
                                                startActivity(Intent(this, MainActivity::class.java))
                                                Toast.makeText(this, "Registrado!", Toast.LENGTH_LONG).show()
                                                Firebase.auth.currentUser?.sendEmailVerification()   //Manda mensaje de verificacion al correo registrado
                                                //                            finish()


                                            } else {

                                                Toast.makeText(this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show() }
                                        }
                                }

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

    //Si existe algún error en el registro con cuenta Google
    fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}