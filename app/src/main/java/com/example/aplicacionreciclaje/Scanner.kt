package com.example.aplicacionreciclaje

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aplicacionreciclaje.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator


class Scanner : Fragment() {

    lateinit var btnScan:Button

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
    }



}