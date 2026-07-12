package id.my.masdepan.adatin.model

data class CartItem (
    val pakaian: PakaianAdat,
    var quantity: Int,
    val size: UkuranPakaian
)