package com.davidlevin40.app

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var animation: Animation = AnimationUtils.loadAnimation(this, R.anim.mytransition)
        findViewById<ImageView>(R.id.iv)?.startAnimation(animation)

        var i: Intent = Intent(this, HomeActivity::class.java)
        var timer : Thread
        timer = Thread() {
            kotlin.run {
                try {
                    sleep(3000)
                } catch (e : InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(i)
                    finish()
                }
            }
        }
        timer.start()
    }
}