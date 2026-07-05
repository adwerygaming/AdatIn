package id.my.masdepan.adatin

import id.my.masdepan.adatin.GlobalVariable.activeAccount

object GlobalFunction {
    fun changePenyewaanStatus(penyewaanId: String, newStatus: StatusSewa): TransactionItem? {
        val penyewaan = activeAccount?.getPurchaseById(penyewaanId)
        if (penyewaan != null) {
            penyewaan.updateStatus(newStatus)
            return penyewaan
        }

        return null
    }

    fun createCustumerAccount(
        data: UserAccount
    ): Boolean {
        val allAccounts = GlobalVariable.accounts
        val existing = allAccounts.find { it.email == data.email }

        // account already exist
        if (existing != null) {
            return false
        }

        allAccounts.add(data as Customer)
        return true
    }

    fun getCustomerAccountByEmail(): UserAccount? {
        val allAccounts = GlobalVariable.accounts
        val account = allAccounts.find { it.email == activeAccount?.email }
        return account
    }
}