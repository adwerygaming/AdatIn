package id.my.masdepan.adatin

data class UserPersonalInformation(
    val fullName: String,
    val address: String,
    val phoneNumber: String
)

data class UserAccount(
    private var password: String,
    private var email: String
)