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
            "TRX-TEST-3",
            3,
            0,
            0,
            TipePengambilan.PICKUP,
            "[TEST]",
            0,
            StatusSewa.DIBATALKAN
        ),
        Penyewaan(
            "TRX-TEST-2",
            3,
            0,
            0,
            TipePengambilan.PICKUP,
            "[TEST]",
            0,
            StatusSewa.SEDANG_DIPROSES
        )
    )

    fun isLoggedIn(): Boolean {
        return akun != null
    }

    fun logout() {
        akun = null
    }
}