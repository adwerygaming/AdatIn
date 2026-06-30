package id.my.masdepan.adatin

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupBottomNav(currentActivity: Activity) {
    this.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                if (currentActivity !is MainActivity) {
                    val intent = Intent(currentActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    currentActivity.startActivity(intent)

                    currentActivity.overridePendingTransition(0, 0)
                }
                true
            }
            R.id.nav_cart -> {
                if (currentActivity !is MyCartActivity) {
                    val intent = Intent(currentActivity, MyCartActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    currentActivity.startActivity(intent)

                    currentActivity.overridePendingTransition(0, 0)
                }
                true
            }
            R.id.nav_account -> {
                if (currentActivity !is MyProfileActivity) {
                    val intent = Intent(currentActivity, MyProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    currentActivity.startActivity(intent)

                    currentActivity.overridePendingTransition(0, 0)
                }
                true
            }
            else -> false
        }
    }
}