package id.my.masdepan.adatin.model

import id.my.masdepan.adatin.R

object GlobalVariable {
    var activeAccount: Customer? = Customer(
        "masdepan@adatin.com",
        "admin#1234",
        "MasDepan",
        "Jl. Amikom",
        "123",
        R.drawable.team_pic_devan
    )
    val accounts: MutableList<Customer> = mutableListOf(
        Customer(
            "masdepan@adatin.com",
            "admin#1234",
            "MasDepan",
            "Jl. Amikom",
            "123",
            R.drawable.team_pic_devan
        ),
        Customer("yoga@adatin.com", "admin#1234", "Yoga A.", null, null, R.drawable.team_pic_yoga),
        Customer("iqbal@adatin.com", "admin#1234", "Iqbal", null, null, null),
        Customer("fikki@adatin.com", "admin#1234", "Fikki", null, null, null),
        Customer("indira@adatin.com", "admin#1234", "Indira", null, null, null),
    )

    val openTime = "09:00"
    val closeTime = "20:00"
    val maxRentingDays = 7;
}