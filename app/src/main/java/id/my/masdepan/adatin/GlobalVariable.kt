package id.my.masdepan.adatin

import kotlin.collections.mutableListOf

object GlobalVariable {
    var activeAccount: Customer? = Customer("masdepan@adatin.com", "admin#1234", "MasDepan", null, null)
    val accounts: MutableList<UserAccount> = mutableListOf(
        Customer("masdepan@adatin.com", "admin#1234", "MasDepan", null, null),
        Customer("yoga@adatin.com", "admin#1234", "Yoga A.", null, null),
        Customer("iqbal@adatin.com", "admin#1234", "Iqbal", null, null),
        Customer("fikki@adatin.com", "admin#1234", "Fikki", null, null),
        Customer("indira@adatin.com", "admin#1234", "Indira", null, null),
    )
}