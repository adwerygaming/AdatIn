package id.my.masdepan.adatin
import id.my.masdepan.adatin.R

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

val daftarPakaian = mutableListOf(
    Pakaian(
        id = 1,
        nama = "Kebaya Kesatrian",
        deskripsi = "Pakaian adat elegan khas keraton, sering dilengkapi dengan sanggul dan perhiasan lengkap.",
        gambar = R.drawable.item_pic_kebayakesatrian,
        rating = 4.5,
        daerah = "DI Yogyakarta",
        harga_per_hari = 150000,
        tersedia = true
    ),
    Pakaian(
        id = 2,
        nama = "Jawi Jangkep",
        deskripsi = "Pakaian resmi pria Jawa berupa jas kerah tutup (beskap), keris, dan blangkon.",
        gambar = R.drawable.item_pic_jawijangkep,
        rating = 4.7,
        daerah = "Jawa Tengah",
        harga_per_hari = 125000,
        tersedia = true
    ),
    Pakaian(
        id = 3,
        nama = "Kebaya Sunda",
        deskripsi = "Kebaya pas badan dengan warna cerah dan kerah bentuk U, dipadukan dengan kain batik.",
        gambar = R.drawable.item_pic_kebayasunda,
        rating = 4.6,
        daerah = "Jawa Barat",
        harga_per_hari = 100000,
        tersedia = true
    ),
    Pakaian(
        id = 4,
        nama = "Sadariah & Kebaya Encim",
        deskripsi = "Pria memakai kemeja tanpa kerah (koko) ala Betawi, wanita memakai kebaya dengan bordiran warna-warni.",
        gambar = R.drawable.item_pic_sadariahkebayaencim,
        rating = 4.8,
        daerah = "Jakarta",
        harga_per_hari = 100000,
        tersedia = true
    ),
    Pakaian(
        id = 5,
        nama = "Pesa'an",
        deskripsi = "Setelan longgar berwarna hitam khas Madura yang dipakai di atas kaus bergaris horizontal merah putih.",
        gambar = R.drawable.item_pic_pesaan,
        rating = 4.4,
        daerah = "Jawa Timur",
        harga_per_hari = 75000,
        tersedia = true
    ),
    Pakaian(
        id = 6,
        nama = "Payas Agung",
        deskripsi = "Pakaian upacara mewah khas Bali yang terkenal dengan mahkota emasnya yang tinggi dan rumit.",
        gambar = R.drawable.item_pic_payasagung,
        rating = 4.9,
        daerah = "Bali",
        harga_per_hari = 300000,
        tersedia = true
    ),
)

enum class UkuranPakaian {
    S, M, L, XL
}