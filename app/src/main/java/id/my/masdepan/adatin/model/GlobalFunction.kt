package id.my.masdepan.adatin.model

object GlobalFunction {
    fun changePenyewaanStatus(penyewaanId: String, newStatus: StatusSewa): TransactionItem? {
        val activeAccount = GlobalVariable.activeAccount
        val penyewaan = activeAccount?.getTransactionById(penyewaanId)
        if (penyewaan != null) {
            penyewaan.updateRentingStatus(newStatus)
            return penyewaan
        }

        return null
    }
}