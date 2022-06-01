package com.example.aplicacionreciclaje

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.aplicacionreciclaje.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var navigationView : NavigationView
    lateinit var txtLogout: TextView


    enum class ProviderType(){
        BASIC,
        GOOGLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val sessionId = this@MainActivity.intent.getStringExtra("uri")

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        txtLogout = findViewById(R.id.btnLogout)


        txtLogout.setOnClickListener {

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)

            FirebaseAuth.getInstance().signOut()


            googleClient.signOut().addOnCompleteListener(this) {
                task->

                if(task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else{

                    Toast.makeText(this, "No puedes cerrar sesion??", Toast.LENGTH_SHORT)
                }

            }

            }



        //Guardar datos de login


        val user = FirebaseAuth.getInstance().currentUser

        navigationView = findViewById(R.id.nav_view)
        val headerView : View = navigationView.getHeaderView(0)
        val navEmail : TextView = headerView.findViewById(R.id.txtCorreoNav)
        val navNombre: TextView = headerView.findViewById(R.id.txtNombreNav)
        val navImg: ImageView = headerView.findViewById(R.id.imgProfile)
        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid

        //Instancia para encontrar la id del usuario logueado
        FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid).addValueEventListener(object :
            ValueEventListener {



            override fun onDataChange(snapshot: DataSnapshot) {


                //identificar valor de los atributos del usaurio
                val nomUsuario: String = snapshot.child("nombre").value.toString()
                val emailUsuario: String = snapshot.child("correo").value.toString()

                navNombre.text = user?.displayName
                navEmail.text =   user?.email


                if(user?.photoUrl != null) {
                        Glide.with(baseContext)
                            .load(user.photoUrl)
                            .into(navImg);

                }

                if(user?.displayName == null){
                    navNombre.text = nomUsuario
                }




            }

            override fun onCancelled(error: DatabaseError) {

            }


        })







        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.products, R.id.mapFragment, R.id.soporte
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}


