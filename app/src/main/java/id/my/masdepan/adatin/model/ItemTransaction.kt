package id.my.masdepan.adatin.model

data class TransactionItem(
    val id: String, // invoice id
    val pakaian: PakaianAdat,
    val qty: Int,
    private val tanggal_mulai_sewa_ms: Long,
    private val tanggal_selesai_sewa_ms: Long,
    private val tipe_pengambilan: TipePengambilan,
    val ukuran: UkuranPakaian,
    private var status: StatusSewa
) {
    fun getStatus(): StatusSewa {
        return status
    }

    fun getPickupMethod(): TipePengambilan {
        return tipe_pengambilan
    }

    fun updateRentingStatus(newStatus: StatusSewa): TransactionItem {
        this.status = newStatus
        return this
    }

    fun getRentingDays(): Int {
        val diffMs = tanggal_selesai_sewa_ms - tanggal_mulai_sewa_ms
        val diffDays = diffMs / (1000L * 60 * 60 * 24) + 1 // so when 1 hari dihitung 1 hari
        return diffDays.toInt().coerceAtLeast(1) // Minimum 1 day
    }

    fun calculateSubtotal(): Int {
        return pakaian.harga_sewa_per_hari * qty * getRentingDays()
    }

    fun calculateDeliveryFee(): Int {
        val deliveryFee = 15000
        val result = if (tipe_pengambilan == TipePengambilan.DELIVERY) deliveryFee else 0
        return result
    }

    fun calculateTotal(): Int {
        return calculateSubtotal() + calculateDeliveryFee()
    }
}