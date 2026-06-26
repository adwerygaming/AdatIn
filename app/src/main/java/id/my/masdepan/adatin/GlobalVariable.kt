package id.my.masdepan.adatin

object GlobalVariable {
    var akun: UserAccount? = UserAccount(
        "12345",
        "test@masdepan.my.id"
    )
    var infoPribadi: UserPersonalInformation? = null
    var semuaTransaksi: MutableList<Penyewaan>? = null

    fun isLoggedIn(): Boolean {
        return akun != null
    }

    fun logout() {
        akun = null
    }
}