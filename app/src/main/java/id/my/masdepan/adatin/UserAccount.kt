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

    var fullName: String,
    var address: String,
    var phoneNumber: String
): UserAccount(email, password) {
    private val semuaTransaksi = mutableListOf<TransactionItem>()

    private val keranjang = mutableListOf<CartItem>()

    fun addPurchaseHistory(transaction: TransactionItem) {
        semuaTransaksi.add(transaction)
    }

    fun addToCart(product: CartItem): CartItem {
        val items = this.getMyCart()

        for (item in items) {
            if (item.pakaianId == product.pakaianId && item.size == product.size) {
                item.quantity += product.quantity
                return item
            }
        }

        this.keranjang.add(product)
        return product
    }

    fun getMyCart(): List<CartItem> {
        return keranjang
    }

    fun removeCartItem(item: CartItem) {
        keranjang.remove(item)
    }

    fun getPurchaseById(transactionId: String): TransactionItem? {
        val allTransactions = semuaTransaksi
        return allTransactions.find { it.id == transactionId }
    }

    fun getMyPurchaseHistory(): List<TransactionItem> {
        return semuaTransaksi
    }

    fun updateName(value: String): Boolean {
        this.fullName = value
        return true
    }

    fun updateEmail(value: String): Boolean {
        super.email = value
        return true
    }

    fun updatePassword(value: String): Boolean {
        super.password = value
        return true
    }
}