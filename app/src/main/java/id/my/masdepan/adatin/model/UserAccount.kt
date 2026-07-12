package id.my.masdepan.adatin.model

open class UserAccount(
    internal var email: String,
    internal var password: String
) {
    fun login(): Boolean {
        val accounts = GlobalVariable.accounts
        val account = accounts.find { it.email == email && it.password == password }

        if (account != null) {
            GlobalVariable.activeAccount = account
            return true
        } else {
            return false
        }
    }

    fun register(): Boolean {
        val accounts = GlobalVariable.accounts
        val account = accounts.find { it.email == email && it.password == password }

        if (account != null) {
            return false
        } else {
            val account = Customer(email, password, "User", null, null, null)
            accounts.add(account)
            return true
        }
    }
}