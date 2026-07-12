package id.my.masdepan.adatin.model

import id.my.masdepan.adatin.R

object GlobalVariable {
    var activeAccount: Customer? = null
    val accounts: MutableList<Customer> = mutableListOf(
        Customer(
            "masdepan@adatin.com",
            "admin#1234",
            "Devan Aditiya",
            "Jl. Ring Road Utara, Ngringin, Condongcatur, Kec. Depok, Kabupaten Sleman, Daerah Istimewa Yogyakarta 55281",
            "0812317766221",
            R.drawable.team_pic_devan
        ),
        Customer("yoga@adatin.com", "admin#1234", "Yoga A.", null, null, R.drawable.team_pic_yoga),
        Customer("iqbal@adatin.com", "admin#1234", "Iqbal", null, null, R.drawable.team_pic_iqbal),
        Customer("fikki@adatin.com", "admin#1234", "Fikki", null, null, R.drawable.team_pic_fikki),
        Customer("indira@adatin.com", "admin#1234", "Indira", null, null, R.drawable.team_pic_indyra),
    )

    val openTime = "09:00"
    val closeTime = "20:00"
    val maxRentingDays = 7
}