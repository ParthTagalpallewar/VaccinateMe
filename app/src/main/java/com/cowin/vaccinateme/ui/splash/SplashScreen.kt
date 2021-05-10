package com.cowin.vaccinateme.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cowin.vaccinateme.ui.main.MainActivity
import com.cowin.vaccinateme.R
import com.cowin.vaccinateme.utils.move
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    val delaySeconds:Long = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()

        // Delay for 2 mins and Move to main Activity
        GlobalScope.launch {

            delay(delaySeconds)

            this@SplashScreen.move(MainActivity::class.java,true)

        }
    }
}