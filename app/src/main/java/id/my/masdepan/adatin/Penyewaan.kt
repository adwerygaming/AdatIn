package id.my.masdepan.adatin

import java.util.Date

enum class TipePengambilan {
    DELIVERY, PICKUP
}

enum class StatusSewa {
    MENUNGGU_PEMBAYARAN,
    DIPROSES,
    SIAP_DIAMBIL,
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
    val subtotal: Int,
    var status: StatusSewa
)

fun updateStatus(id: String, newStatus: StatusSewa): Penyewaan? {
    val penyewaan = GlobalVariable.semuaTransaksi?.find { it.id == id }
    if (penyewaan != null) {
        penyewaan.status = newStatus
        return penyewaan
    }

    return null
}