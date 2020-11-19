package com.davidlevin40.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class StudentMain : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_main)
        val firstName : EditText? = findViewById(R.id.studentName)
        val lastName : EditText? = findViewById(R.id.studentName)
        val studentId : EditText? = findViewById(R.id.studentID)
        val schoolName : EditText ? = findViewById(R.id.schoolName)


        findViewById<Button>(R.id.signButtonStudent)?.setOnClickListener {

            val firstNameStud = firstName?.text.toString().trim()
            val lastNameStud = lastName?.text.toString().trim()
            val studentID = studentId?.text.toString().trim()
            val schoolData = schoolName?.text.toString().trim()
            var flag: Int? = 0

            when {
                schoolData.isEmpty() -> {
                    schoolName?.error = "שדה חסר";
                    schoolName?.requestFocus()
                    flag = 1
                }
                firstNameStud.isEmpty() -> {
                    firstName?.error = "שדה חסר";
                    firstName?.requestFocus()
                    flag = 1
                }
                lastNameStud.isEmpty() -> {
                    lastName?.error = "שדה חסר";
                    lastName?.requestFocus()
                    flag = 1
                }
                studentID.isEmpty() -> {
                    studentId?.error = "שדה חסר";
                    studentId?.requestFocus()
                    flag = 1
                }
                TextUtils.getTrimmedLength(studentId?.text.toString()) != 9 -> {
                    studentId?.error = "שדה לא חוקי";
                    studentId?.requestFocus()
                    flag = 1
                }
                flag == 0 -> {
                    checkStudentData();
                }
            }
        }
    }

    /**
     * Method role: open the second student screen activity - the sign
     */
    private fun openStudentSign() {
        val intent = Intent(this, StudentSign::class.java)
        startActivity(intent)
    }


    private fun checkStudentData() {
        val database : DatabaseReference = FirebaseDatabase.getInstance().reference
        val studentId : EditText? = findViewById(R.id.studentID)
        val schoolName : EditText ? = findViewById(R.id.schoolName)
        val studentIDs = studentId?.text.toString().trim()
        val schoolData = schoolName?.text.toString().trim()

        val ref = FirebaseDatabase.getInstance().getReference("Institution").child(schoolData)
        database.child("Institution").child(schoolData).orderByChild("id").
        equalTo(studentIDs).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.children.iterator().hasNext()) {
                    // updating the signature status
                    ref.child(studentIDs).child("signatureStat").setValue("כן")

                    val date : Date = Date()
                    val formatter : SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
                    val today : String = formatter.format(date)
                    // updating the date
                    ref.child(studentIDs).child("se_ID").setValue(today)

                    openStudentSign();
                } else {
                    Toast.makeText(this@StudentMain, "תלמיד לא מופיע במערכת", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@StudentMain, "אנא הזן שנית את שם המוסד ו/או את פרטי התלמיד", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

}
