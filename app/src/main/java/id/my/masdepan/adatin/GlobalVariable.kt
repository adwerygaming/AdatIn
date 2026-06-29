package id.my.masdepan.adatin

import kotlin.collections.mutableListOf

object GlobalVariable {
    var akun: UserAccount? = UserAccount(
        "12345",
        "test@masdepan.my.id"
    )
    var infoPribadi: UserPersonalInformation? = null
    val semuaTransaksi = mutableListOf(
        Penyewaan(
            "TRX-TEST",
            1,
            0,
            0,
            TipePengambilan.DELIVERY,
            "[TEST]",
            0,
            StatusSewa.DIBATALKAN
        ),
        Penyewaan(
            "TRX-TEST",
            2,
            0,
            0,
            TipePengambilan.DELIVERY,
            "[TEST]",
            0,
            StatusSewa.DIPROSES
        )
    )

    fun isLoggedIn(): Boolean {
        return akun != null
    }

    fun logout() {
        akun = null
    }
}