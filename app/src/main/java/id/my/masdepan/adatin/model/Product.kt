package id.my.masdepan.adatin.model

abstract class Product(
    open val id: Int,
    open val nama: String,
    open val deskripsi: String,
    open val gambar: Int, // Drawable resource ID
    open val rating: Double,
    open val harga_sewa_per_hari: Int,
    open val tersedia: Boolean
)