package id.my.masdepan.adatin


open class UserAccount(
    private var password: String,
    private var email: String
)

class UserPersonalInformation(
    email: String,
    password: String,
    val fullName: String,
    val address: String,
    val phoneNumber: String
): UserAccount(email, password);