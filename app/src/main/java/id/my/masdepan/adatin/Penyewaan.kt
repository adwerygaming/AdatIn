package id.my.masdepan.adatin

import java.util.Date

enum class TipePengambilan {
    DELIVERY, PICKUP
}

enum class StatusSewa {
    MENUNGGU_PEMBAYARAN,
    SEDANG_DIPROSES,
    SIAP_DIAMBIL,
    SEDANG_DIANTAR,
    SEDANG_DISEWA,
    SELESAI,
    DIBATALKAN
}

data class Penyewaan(
    val id: String,
    val pakaianId: Int,
    val tanggal_mulai_sewa_ms: Long,
    val tanggal_selesai_sewa_ms: Long,
    val tipe_pengambilan: TipePengambilan,
    val ukuran: String,
    val subtotal: Int,
    var status: StatusSewa
)

fun updateStatus(transactionId: String, newStatus: StatusSewa): Penyewaan? {
    val penyewaan = GlobalVariable.semuaTransaksi?.find { it.id == transactionId }

    if (penyewaan != null) {
        penyewaan.status = newStatus
        return penyewaan
    }

    return null
}