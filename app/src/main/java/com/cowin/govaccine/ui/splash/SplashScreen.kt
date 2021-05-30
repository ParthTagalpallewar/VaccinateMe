package com.cowin.govaccine.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.cowin.govaccine.R
import com.cowin.govaccine.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SplashScreen : AppCompatActivity() {

    val delaySeconds: Long = 1700L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val root = findViewById<View>(R.id.splash_root) as ConstraintLayout

        img_vaccine.animation = AnimationUtils.loadAnimation(this, R.anim.vaccine_animation)
        appName.animation = AnimationUtils.loadAnimation(this, R.anim.appname_animation)

        val offsets = IntArray(root.childCount) { Random().nextInt() % 600 }.asList()
        var i = 0
        for (view in root.children) {
            if (view.tag == "v_fade") {
                var anim = AnimationUtils.loadAnimation(this, R.anim.v_animation)
                anim.interpolator = AccelerateDecelerateInterpolator()
                anim.startOffset = (900 + offsets[i++]).toLong()
                anim.setAnimationListener(object: Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        view.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
                view.animation = anim
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Delay for 2 mins and Move to main Activity
        GlobalScope.launch {

            delay(delaySeconds)

            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }
}