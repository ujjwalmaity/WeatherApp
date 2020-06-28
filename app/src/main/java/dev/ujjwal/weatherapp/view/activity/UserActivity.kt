package dev.ujjwal.weatherapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.ujjwal.weatherapp.R
import dev.ujjwal.weatherapp.util.PREF_USER_NAME
import dev.ujjwal.weatherapp.util.getUserDetails
import dev.ujjwal.weatherapp.util.showToast
import dev.ujjwal.weatherapp.util.storeUserDetails
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        var userName = getUserDetails(PREF_USER_NAME)

        if (userName != "") {
            gotoMainActivity()
        }

        btn_submit.setOnClickListener {
            userName = et_user_name.text.toString().trim()
            if (userName.isEmpty()) {
                showToast("Please enter user name")
            } else {
                storeUserDetails(PREF_USER_NAME, userName)
                gotoMainActivity()
            }
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}