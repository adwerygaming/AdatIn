package id.my.masdepan.adatin.model

data class PakaianAdat(
    override val id: Int,
    override val nama: String,
    override val deskripsi: String,
    override val gambar: Int,
    override val rating: Double,
    override val harga_sewa_per_hari: Int,
    override val tersedia: Boolean,

    val daerah: String,
) : Product(id, nama, deskripsi, gambar, rating, harga_sewa_per_hari, tersedia)