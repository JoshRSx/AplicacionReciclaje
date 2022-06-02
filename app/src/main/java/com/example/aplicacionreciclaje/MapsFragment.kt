package com.example.aplicacionreciclaje

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap



    private val callback = OnMapReadyCallback { googleMap ->


        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(-12.031304378464831, -77.04340854273143), 16f)
        )


        val lider = LatLng(-12.03022002084803, -77.04449888916382)
        googleMap.addMarker(
            MarkerOptions()
                .position(lider)
                .title("Farmacia Lider+")
        )

        val boticasSalud = LatLng(-12.031937510948149, -77.04325501091448)
        googleMap.addMarker(
            MarkerOptions()
                .position(boticasSalud)
                .title("Boticas & Salud+")
        )

        val inkaFarma = LatLng( -12.033612057084582, -77.04556717637546)
        googleMap.addMarker(
            MarkerOptions()
                .position(inkaFarma)
                .title("Inkafarma")
        )


        val MiFarma = LatLng( -12.02730025096127, -77.04607932295433)
        googleMap.addMarker(
            MarkerOptions()
                .position(MiFarma)
                .title("Mifarma")
        )

    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_maps, container, false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }





}