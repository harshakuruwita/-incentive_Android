package com.fitscorp.sl.apps.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class WelcomeLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_login)
   //     AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
Log.d("TAG_LOG","Spalsh")
/*
        btn_login.setOnClickListener {
            LoginActivity.startActivity(this@SplashActivity)
        }
*/


    }
}
