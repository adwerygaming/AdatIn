package id.my.masdepan.adatin.model

import id.my.masdepan.adatin.R

class Customer(
    email: String,
    password: String,

    private var fullName: String,
    private var address: String?,
    private var phoneNumber: String?,
    private var profilePhoto: Int?
): UserAccount(email, password) {
    private val semuaTransaksi = mutableListOf<TransactionItem>()
    private val keranjang = mutableListOf<CartItem>()

    fun addPurchaseHistory(transaction: TransactionItem) {
        semuaTransaksi.add(transaction)
    }

    fun addToCart(product: CartItem): CartItem {
        val items = this.getMyCart()

        for (item in items) {
            if (item.pakaian == product.pakaian && item.size == product.size) {
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

    fun getTransactionById(transactionId: String): TransactionItem? {
        val allTransactions = semuaTransaksi
        return allTransactions.find { it.id == transactionId }
    }

    fun getMyTransactionHistory(): List<TransactionItem> {
        return semuaTransaksi
    }

    fun getProfilePhoto(): Int {
        return profilePhoto ?: R.drawable.user_placeholder;
    }

    fun getName(): String {
        return fullName
    }

    fun getEmail(): String {
        return super.email
    }

    fun getPhoneNumber(): String? {
        return phoneNumber
    }

    fun getAddress(): String? {
        return address
    }

    fun updateName(value: String): Boolean {
        this.fullName = value
        return true
    }

    fun updateEmail(value: String): Boolean {
        super.email = value
        return true
    }

    fun updatePhoneNumber(value: String): Boolean {
        this.phoneNumber = value
        return true
    }

    fun updateAddress(value: String): Boolean {
        this.address = value
        return true
    }

    fun updateProfilePhoto(value: Int): Boolean {
        this.profilePhoto = value
        return true
    }

    fun updatePassword(value: String): Boolean {
        super.password = value
        return true
    }
}