package id.my.masdepan.adatin.model

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