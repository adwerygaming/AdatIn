package id.my.masdepan.adatin

open class UserAccount(
    internal var email: String,
    internal var password: String
) {
    fun login(email: String, password: String): Boolean {
        return this.email == email && this.password == password
    }

}

class Customer(
    email: String,
    password: String,

    val fullName: String,
    val address: String,
    val phoneNumber: String
): UserAccount(email, password) {
    private val semuaTransaksi = mutableListOf(
        Penyewaan(
            "TRX-TEST-3",
            3,
            0,
            0,
            TipePengambilan.PICKUP,
            "[TEST]",
            0,
            StatusSewa.DIBATALKAN
        ),
        Penyewaan(
            "TRX-TEST-2",
            3,
            0,
            0,
            TipePengambilan.PICKUP,
            "[TEST]",
            0,
            StatusSewa.SEDANG_DIPROSES
        )
    )

    //! somehow i think we need to store the size as well
    private val keranjang = mutableListOf<Pakaian>()

    fun addPurchaseHistory(transaction: Penyewaan) {
        semuaTransaksi.add(transaction)
    }

    fun addToCart(product: Pakaian) {
        keranjang.add(product)
    }

    fun getMyCart(): List<Pakaian> {
        return keranjang
    }

    fun getPurchaseById(transactionId: String): Penyewaan? {
        val allTransactions = semuaTransaksi
        return allTransactions.find { it.id == transactionId }
    }

    fun getMyPurchaseHistory(): List<Penyewaan> {
        return semuaTransaksi
    }
}