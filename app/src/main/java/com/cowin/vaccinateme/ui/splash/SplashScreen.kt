package com.cowin.vaccinateme.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.ui.main.MainActivity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreen : AppCompatActivity() {

    val delaySeconds: Long = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        MobileAds.initialize(
            this
        )

    }

    override fun onStart() {
        super.onStart()

        // Delay for 2 mins and Move to main Activity
        GlobalScope.launch {

            delay(delaySeconds)


            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()

        }
    }
}