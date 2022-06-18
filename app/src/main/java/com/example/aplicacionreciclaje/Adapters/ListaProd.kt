package com.example.aplicacionreciclaje.Adapters

import android.os.Parcelable
import android.widget.Button
import java.io.Serializable

open class ListaProd: Serializable {

    //Atributos drawable/product_item

    var cod:Int = 0
    var nom:String = ""
    var desc:String = ""
    var puntos:Int = 0
    var prereg:Double = 0.0
    var predesc:Double = 0.0
    var imgprod:Int = 0

constructor(){}

    constructor(cod:Int, nom:String, desc:String, puntos:Int, prereg:Double, predesc:Double, imgprod:Int){

        this.cod = cod
        this.desc = desc
        this.puntos = puntos
        this.prereg = prereg
        this.predesc = predesc
        this.imgprod = imgprod

    }



}