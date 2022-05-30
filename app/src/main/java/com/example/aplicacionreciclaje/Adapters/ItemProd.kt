package com.example.aplicacionreciclaje.Adapters

class ItemProd {

    var nomProd: String = ""
    var puntosProd: String = ""
    var descripcionProd: String = ""
    var imgProd: Int = 0

    var precioRegProd: Double = 0.0
    var precioDescProd: Double = 0.0


    constructor(
        nomProd: String,
        puntosProd: String,
        descripcionProd: String,
        imgProd: Int,
        precioRegProd: Double,
        precioDescProd: Double
    ) {
        this.nomProd = nomProd
        this.puntosProd = puntosProd
        this.descripcionProd = descripcionProd
        this.imgProd = imgProd
        this.precioRegProd = precioRegProd
        this.precioDescProd = precioDescProd
    }
}