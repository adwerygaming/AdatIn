package id.my.masdepan.adatin

import kotlin.collections.mutableListOf

object GlobalVariable {
    var activeAccount: Customer? = Customer("masdepan@masdepan.my.id", "admin#1234", "MasDepan", "123", "08123123123") // dumy
    val accounts: MutableList<UserAccount> = mutableListOf(
        Customer("masdepan@masdepan.my.id", "admin#1234", "MasDepan", "123", "08123123123") // dumy
    )
}