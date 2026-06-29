package id.my.masdepan.adatin

import id.my.masdepan.adatin.GlobalVariable.activeAccount

object GlobalFunction {
    fun changePenyewaanStatus(penyewaanId: String, newStatus: StatusSewa): Penyewaan? {
        val penyewaan = activeAccount?.getPurchaseById(penyewaanId)
        if (penyewaan != null) {
            penyewaan.updateStatus(newStatus)
            return penyewaan
        }

        return null
    }
}