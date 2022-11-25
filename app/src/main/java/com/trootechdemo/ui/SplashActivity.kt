package com.trootechdemo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trootechdemo.R
import com.trootechdemo.ui.chat.ChatUserActivity
import com.trootechdemo.ui.main.MainActivity
import com.trootechdemo.utils.Constants
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setTimer()
    }

    private fun setTimer() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, ChatUserActivity::class.java))
            }
        }, Constants.SPLASH_TIMEOUT)
    }

}