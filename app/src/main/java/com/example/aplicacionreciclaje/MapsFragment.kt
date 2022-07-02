package com.example.aplicacionreciclaje

import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap



    private val callback = OnMapReadyCallback { googleMap ->


        //Coordenada de acercamiento
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(-12.064215217540083, -77.03741645156602), 16f)
        )


        //Puntos de reciclaje

        val ramblaBrasil = LatLng(-12.06602958291968, -77.04760273568611)
        googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconmarker2))  //Icono
                .position(ramblaBrasil)
                .title("La Rambla (Av. Brasil)")
        )

        val realPlazaCentro = LatLng(-12.05641569831374, -77.03745879847507)
        googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconmarker2))
                .position(realPlazaCentro)
                .title("Real Plaza Centro Cívico")
        )

        val ccPolvosAzules = LatLng( -12.061652450107653, -77.03422441095653)
        googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconmarker2))
                .position(ccPolvosAzules)
                .title("CC. Polvos Azules")
        )


        val ccMancoCapac = LatLng( -12.062711928865099, -77.02906325422656)
        googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconmarker2))
                .position(ccMancoCapac)
                .title("CC. Manco Cápac")
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