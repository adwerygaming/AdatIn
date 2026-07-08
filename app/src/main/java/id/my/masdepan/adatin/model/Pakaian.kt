package id.my.masdepan.adatin.model

data class Pakaian(
    val id: Int,
    val nama: String,
    val deskripsi: String,
    val gambar: Int,
    val rating: Double,
    val daerah: String,
    val harga_per_hari: Int,
    val tersedia: Boolean
)