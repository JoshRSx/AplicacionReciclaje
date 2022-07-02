package com.example.aplicacionreciclaje.Adapters

import java.io.Serializable

class ItemSectionProd : Serializable {

    var nomSeccion : String = ""
    var imgSection: Int = 0



    constructor(nomSeccion: String, imgSection:Int){
        this.nomSeccion = nomSeccion
        this.imgSection = imgSection

    }
}