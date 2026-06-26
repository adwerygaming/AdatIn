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
    val tanggal_masuk: Date,
    val tanggal_keluar: Date,
    val tipe_pengambilan: TipePengambilan,
    var status: StatusSewa
)

fun lunaskanSewaan(id: String): Penyewaan? {
    val sewaan = GlobalVariable.semuaTransaksi?.find { it.id == id }

    if (sewaan != null) {
        // add checks
        if (sewaan.tipe_pengambilan == TipePengambilan.PICKUP) {
            sewaan.status = StatusSewa.SIAP_DIAMBIL
        } else {
            sewaan.status = StatusSewa.DIPROSES
        }

        return sewaan
    } else {
        return null
    }
}

fun sewa(pakaianId: Int, tanggal_masuk: Date, tanggal_keluar: Date, tipe_pengambilan: TipePengambilan): Penyewaan {
    val penyewaan = Penyewaan(
        "TRX-${System.currentTimeMillis()}",
        pakaianId,
        tanggal_masuk,
        tanggal_keluar,
        tipe_pengambilan,
        StatusSewa.MENUNGGU_PEMBAYARAN
    )

    // add checks here
    GlobalVariable.semuaTransaksi?.add(penyewaan)
    return penyewaan
}

fun kembalikan(pakaianId: Int): Penyewaan? {
    val sewaan = GlobalVariable.semuaTransaksi?.find { it.pakaianId == pakaianId }

    if (sewaan != null) {
        // add checks
        GlobalVariable.semuaTransaksi?.remove(sewaan)
        return sewaan
    } else {
        return null
    }
}