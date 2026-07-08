package id.my.masdepan.adatin.model

import id.my.masdepan.adatin.UkuranPakaian

data class CartItem (
    val pakaianId: Int,
    var quantity: Int,
    val size: UkuranPakaian
)