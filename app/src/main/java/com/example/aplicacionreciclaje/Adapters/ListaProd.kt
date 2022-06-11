package com.example.aplicacionreciclaje.Adapters

import android.widget.Button
import java.io.Serializable

class ListaProd:Serializable {

    //Atributos drawable/product_item

    var cod:Int = 0
    var nom:String = ""
    var desc:String = ""
    var puntos:Int = 0
    var prereg:Double = 0.0
    var predesc:Double = 0.0
    var imgprod:Int = 0


}