package id.my.masdepan.adatin

data class Pakaian(
    val id: Int,
    val nama: String,
    val deskripsi: String,
    val gambar: String, // use placeholder for now
    val daerah: String,
    val harga_per_hari: Int,
    val tersedia: Boolean
)

val daftarPakaian = mutableListOf<Pakaian>(
    Pakaian(
        id = 1,
        nama = "Ulee Balang",
        deskripsi = "Pakaian adat yang dulunya hanya dipakai oleh keluarga kerajaan atau ulama besar.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Aceh",
        harga_per_hari = 150000,
        tersedia = true
    ),
    Pakaian(
        id = 2,
        nama = "Ulos",
        deskripsi = "Kain tenun khas Batak yang sering diselempangkan, simbol restu dan kasih sayang.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Sumatera Utara",
        harga_per_hari = 100000,
        tersedia = true
    ),
    Pakaian(
        id = 3,
        nama = "Bundo Kanduang",
        deskripsi = "Pakaian adat wanita Minangkabau dengan ciri khas penutup kepala berbentuk tanduk kerbau.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Sumatera Barat",
        harga_per_hari = 175000,
        tersedia = false
    ),
    Pakaian(
        id = 4,
        nama = "Aesan Gede",
        deskripsi = "Pakaian kebesaran kerajaan Sriwijaya, didominasi warna merah dan emas yang mewah.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Sumatera Selatan",
        harga_per_hari = 200000,
        tersedia = true
    ),
    Pakaian(
        id = 5,
        nama = "Teluk Belanga",
        deskripsi = "Pakaian khas pria Melayu yang dipadukan dengan kain songket di bagian pinggang.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Kepulauan Riau",
        harga_per_hari = 120000,
        tersedia = true
    ),
    Pakaian(
        id = 6,
        nama = "Kebaya Encim",
        deskripsi = "Perpaduan budaya Tionghoa dan Betawi, berbahan tipis dengan bordir floral yang khas.",
        gambar = "https://via.placeholder.com/150",
        daerah = "DKI Jakarta",
        harga_per_hari = 85000,
        tersedia = true
    ),
    Pakaian(
        id = 7,
        nama = "Pangsi",
        deskripsi = "Setelan kemeja dan celana longgar berwarna hitam, identik dengan pakaian pendekar.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Banten",
        harga_per_hari = 70000,
        tersedia = true
    ),
    Pakaian(
        id = 8,
        nama = "Kebaya Sunda",
        deskripsi = "Kebaya elegan dengan potongan leher U atau V, dipadukan dengan kain batik kebat.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Jawa Barat",
        harga_per_hari = 110000,
        tersedia = false
    ),
    Pakaian(
        id = 9,
        nama = "Jawi Jangkep",
        deskripsi = "Pakaian resmi pria Jawa Tengah berupa beskap, blangkon, dan kain jarik.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Jawa Tengah",
        harga_per_hari = 130000,
        tersedia = true
    ),
    Pakaian(
        id = 10,
        nama = "Kebaya Kesatrian",
        deskripsi = "Pakaian adat elegan khas keraton, sering dilengkapi dengan sanggul dan perhiasan lengkap.",
        gambar = "https://via.placeholder.com/150",
        daerah = "DI Yogyakarta",
        harga_per_hari = 150000,
        tersedia = true
    ),
    Pakaian(
        id = 11,
        nama = "Pesa'an",
        deskripsi = "Setelan kaos garis merah putih dan baju luar hitam longgar khas masyarakat Madura.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Jawa Timur",
        harga_per_hari = 60000,
        tersedia = true
    ),
    Pakaian(
        id = 12,
        nama = "Payas Agung",
        deskripsi = "Pakaian mewah bernuansa emas dan mahkota tinggi, biasanya digunakan untuk pernikahan.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Bali",
        harga_per_hari = 250000,
        tersedia = true
    ),
    Pakaian(
        id = 13,
        nama = "Baju Bodo",
        deskripsi = "Pakaian adat tertua di dunia, berbentuk segi empat dengan lengan pendek, berbahan transparan.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Sulawesi Selatan",
        harga_per_hari = 140000,
        tersedia = false
    ),
    Pakaian(
        id = 14,
        nama = "Baju Cele",
        deskripsi = "Kain tebal bermotif kotak-kotak kecil berwarna merah putih yang bernuansa ceria.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Maluku",
        harga_per_hari = 90000,
        tersedia = true
    ),
    Pakaian(
        id = 15,
        nama = "Baju Kurung Ewer",
        deskripsi = "Pakaian adat dengan rok yang terbuat dari susunan daun sagu atau jerami kering.",
        gambar = "https://via.placeholder.com/150",
        daerah = "Papua Barat",
        harga_per_hari = 100000,
        tersedia = true
    )
)

enum class UkuranPakaian {
    S, M, L, XL
}