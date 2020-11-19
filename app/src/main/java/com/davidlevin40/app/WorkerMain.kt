package com.davidlevin40.app

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class WorkerMain : AppCompatActivity() {

    lateinit var editName: EditText
    lateinit var editLastName: EditText
    lateinit var editStudentID : EditText
    lateinit var editSchoolName: EditText
    lateinit var beforeSignature: String
    lateinit var editDeleteStudent: EditText
    lateinit var btnAddData: Button
    lateinit var btnDeleteData: Button
    lateinit var btnViewData: Button

    lateinit var databaseStudents: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_main)

        editName = findViewById(R.id.studentName)
        editLastName = findViewById(R.id.studentLastName)
        editStudentID = findViewById(R.id.studentID)
        editSchoolName = findViewById(R.id.schoolName)
        editDeleteStudent = findViewById(R.id.DataBaseID)
        btnAddData = findViewById(R.id.adding_student_button)
        btnDeleteData = findViewById(R.id.deleting_student_button)
        btnViewData = findViewById(R.id.button_viewAll)
        beforeSignature = "לא"

        btnAddData.setOnClickListener {
            addStudent()
        }

        btnDeleteData.setOnClickListener {
            deleteStudent()
        }

        btnViewData.setOnClickListener {
            viewStudentData()
        }
    }

    private fun EditText.clear() {
        text.clear()
    }

    private fun addStudent() {
        var firstName = editName.getText().toString().trim()
        var lastName = editLastName.getText().toString().trim()
        var studID = editStudentID.text.toString().trim()
        var schoolName = editSchoolName.text.toString().trim()
        var flag: Int? = 0

        when {
            schoolName.isEmpty() -> {
                editSchoolName.error = "שדה חסר";
                editSchoolName.requestFocus()
                flag = 1
            }
            firstName.isEmpty() -> {
                editName.error = "שדה חסר";
                editName.requestFocus()
                flag = 1
            }
            lastName.isEmpty() -> {
                editLastName.error = "שדה חסר";
                editLastName.requestFocus()
                flag = 1
            }
            studID.isEmpty() -> {
                editStudentID.error = "שדה חסר";
                editStudentID.requestFocus()
                flag = 1
            }
            TextUtils.getTrimmedLength(editStudentID.text.toString()) != 9 -> {
                editStudentID.error = "שדה לא חוקי";
                editStudentID.requestFocus()
                flag = 1
            }
            flag == 0 -> {
                val ref = FirebaseDatabase.getInstance().getReference("Institution")

                val date : Date = Date()
                val formatter : SimpleDateFormat = SimpleDateFormat("MM/dd/yyyy")
                val day : String = formatter.format(date)

                val student = RegiStudent(day, firstName, lastName, studID, "לא")
                ref.child(schoolName).child(studID).setValue(student).addOnCompleteListener {
                    Toast.makeText(this, "הוזן בהצלחה", Toast.LENGTH_SHORT).show()

                    editName.clear()
                    editLastName.clear()
                    editStudentID.clear()
                }
            }
        }
    }

    private fun deleteStudent() {
        var studID = editDeleteStudent.getText().toString().trim()
        var schoolName = editSchoolName.getText().toString().trim()
        var flag: Int? = 0

        when {
            schoolName.isEmpty() -> {
                editSchoolName.error = "שדה חסר";
                editSchoolName.requestFocus()
            }
            studID.isEmpty() -> {
                editDeleteStudent.error = "שדה חסר";
                editDeleteStudent.requestFocus()
                flag = 1
            }
            TextUtils.getTrimmedLength(editDeleteStudent.text.toString()) != 9 -> {
                editDeleteStudent.error = "שדה לא חוקי";
                editDeleteStudent.requestFocus()
                flag = 1
            }
            flag == 0 -> {
                val ref = FirebaseDatabase.getInstance().getReference("Institution").child(schoolName).child(studID)
                ref.removeValue()
                Toast.makeText(this, "מידע נמחק", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "לא קיים במערכת", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun viewStudentData() {
        val intent = Intent(this, WorkerMainScreen::class.java)
        intent.putExtra("alert", "viewStudentData")
        startActivity(intent)
    }
}