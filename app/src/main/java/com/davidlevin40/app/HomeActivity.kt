package com.davidlevin40.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        findViewById<Button>(R.id.students_button)?.setOnClickListener {
            openStudentActivity()
        }

        findViewById<Button>(R.id.workers_button)?.setOnClickListener {
            openWorkerActivity()
        }
    }

    private fun openStudentActivity() {
        var i: Intent = Intent(this, StudentMain::class.java)
        startActivity(i)
    }

    private fun openWorkerActivity() {
        var j: Intent = Intent(this, LoginActivity::class.java)
        startActivity(j)
    }

}

