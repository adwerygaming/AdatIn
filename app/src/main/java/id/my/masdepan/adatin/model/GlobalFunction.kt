package id.my.masdepan.adatin.model

object GlobalFunction {
    fun changePenyewaanStatus(penyewaanId: String, newStatus: StatusSewa): TransactionItem? {
        val penyewaan = GlobalVariable.activeAccount?.getPurchaseById(penyewaanId)
        if (penyewaan != null) {
            penyewaan.updateStatus(newStatus)
            return penyewaan
        }

        return null
    }
}