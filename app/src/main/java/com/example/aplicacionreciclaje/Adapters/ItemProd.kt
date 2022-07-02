package com.example.aplicacionreciclaje.Adapters

import java.io.Serializable

open class ItemProd:Serializable {

    var nomProd: String = ""
    var puntosProd: Int = 0
    var descripcionProd: String = ""
    var imgProd: Int = 0
    var precioRegProd: Double = 0.0
    var precioDescProd: Double = 0.0

    constructor()

    constructor(nomProd: String, puntosProd: Int, descripcionProd: String, imgProd: Int, precioRegProd: Double, precioDescProd: Double) {
        this.nomProd = nomProd
        this.puntosProd = puntosProd
        this.descripcionProd = descripcionProd
        this.imgProd = imgProd
        this.precioRegProd = precioRegProd
        this.precioDescProd = precioDescProd
    }
}