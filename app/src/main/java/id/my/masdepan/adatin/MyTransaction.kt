package id.my.masdepan.adatin

enum class TipePengambilan {
    DELIVERY, PICKUP
}

enum class JenisKetersediaan {
    TERSEDIA, KOSONG
}

enum class StatusSewa {
    MENUNGGU_PEMBAYARAN,
    SEDANG_DIPROSES,
    SIAP_DIAMBIL,
    SAMPAI_TUJUAN,
    SEDANG_DIANTAR,
    SEDANG_DISEWA,
    MENUNGGU_KONFIRMASI_PEMBATALAN, // used after user canceling their order
    SELESAI,
    DIBATALKAN
}

data class TransactionItem(
    val id: String, // invoice id
    val pakaianId: Int,
    val tanggal_mulai_sewa_ms: Long,
    val tanggal_selesai_sewa_ms: Long,
    val tipe_pengambilan: TipePengambilan,
    val ukuran: String,
    val subtotal: Int,
    var status: StatusSewa
) {
    fun updateStatus(newStatus: StatusSewa): TransactionItem? {
        this.status = newStatus
        return this
    }
}

