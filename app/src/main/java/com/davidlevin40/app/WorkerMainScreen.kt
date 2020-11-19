package com.davidlevin40.app

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import android.widget.Toast.makeText as makeText1

class WorkerMainScreen : AppCompatActivity() {

    lateinit var text : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_main_screen)


        if (savedInstanceState == null) {
            val extras : Bundle? = intent.extras;
            if (extras == null) {}
            else {
                val method : String? = extras.getString("alert")
                if (method.equals("viewStudentData")) {
                    alert()
                }
            }
        }

        findViewById<Button>(R.id.log_out)?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            logOutFunc()
            makeText1(this, "Successfully Logged Out", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.health_student_list_button)?.setOnClickListener {
            alert()
        }

        findViewById<Button>(R.id.student_list_button)?.setOnClickListener {
            openEditStudentListActivity()
        }
    }

    private fun openEditStudentListActivity() {
        val j: Intent = Intent(this, WorkerMain::class.java)
        startActivity(j)
    }

    private fun logOutFunc() {
        val k: Intent = Intent(this, HomeActivity::class.java)
        startActivity(k)
    }

    private fun alert() {
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("אנא הכנס את שם המוסד")
        val ans : EditText = EditText(this)
        ans.inputType = InputType.TYPE_CLASS_TEXT
        alert.setView(ans)

        alert.setPositiveButton("המשך", DialogInterface.OnClickListener { dialogInterface, i ->
            text = ans.text.toString()
            makeText1(this, "בקשתך בטיפול", Toast.LENGTH_SHORT).show()
            val schoolName : String = text
            updateListDates(schoolName)
        })

        alert.setNegativeButton("חזור", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        })

        alert.show()

    }

    private fun sendString() {
        val schoolName : String = text
        val k: Intent = Intent(this, StudentList::class.java)
        k.putExtra("School_Name", schoolName)
        startActivity(k)
    }

    private fun updateListDates(schoolName : String) {
        val date : Date = Date()
        val formatter : SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
        val today : String = formatter.format(date)
        val ref = FirebaseDatabase.getInstance().getReference("Institution").child(schoolName)


        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                children.forEach {
                    if (it.child("se_ID").value.toString() != today) {
                        val temp : String = it.child("id").value.toString()
                        ref.child(temp).child("signatureStat").setValue("לא")
                    }
                }
                sendString()
            }
        })
    }
}