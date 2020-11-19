package com.davidlevin40.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class StudentFinalScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_final_screen)

        var progressBar : ProgressBar? = findViewById(R.id.progressBar)
        val loadingTextPart1 : TextView? = findViewById(R.id.LoadingCompleteTextView)
        val loadingTextPart2 : TextView? = findViewById(R.id.LoadingCompleteTextView2)
        val photo : ImageView? = findViewById(R.id.logoHomeScreen)
        var progressStatus = 0

        var mHandler: Handler? = Handler()
        var mThread: Unit = Thread(
            Runnable {
                while (progressStatus < 100) {
                    progressStatus++
                    android.os.SystemClock.sleep(25)
                    mHandler?.post {
                        progressBar?.progress = progressStatus
                    }
                }
                mHandler?.post {
                    loadingTextPart1?.visibility = View.VISIBLE
                    loadingTextPart2?.visibility = View.VISIBLE
                    photo?.visibility = View.VISIBLE
                    progressBar?.visibility = View.INVISIBLE
                }
            }
        ).start()
    }
}

