package com.davidlevin40.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class StudentSign : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_health_sign)

        val finalStageStudentSign : Button? = findViewById(R.id.finish_button)
        val parentName : EditText? = findViewById(R.id.parent_name_edit_text)
        val ID : EditText? = findViewById(R.id.parentID_edit_text)
        val parentSignature : EditText? = findViewById(R.id.parent_signature_edit_text)

        finalStageStudentSign?.setOnClickListener {
            val parentNameData = parentName?.text.toString().trim()
            val parentID = ID?.text.toString().trim()
            val parentSignatureData = parentSignature?.text.toString().trim()
            var flag: Int? = 0

            when {
                parentNameData.isEmpty() -> {
                    parentName?.error = "שדה חסר";
                    parentName?.requestFocus()
                    flag = 1
                }
                parentSignatureData.isEmpty() -> {
                    parentSignature?.error = "שדה חסר";
                    parentSignature?.requestFocus()
                    flag = 1
                }
                parentID.isEmpty() -> {
                    ID?.error = "שדה חסר";
                    ID?.requestFocus()
                    flag = 1
                }
                TextUtils.getTrimmedLength(ID?.text.toString()) != 9 -> {
                    ID?.error = "שדה לא חוקי";
                    ID?.requestFocus()
                    flag = 1
                }
                flag == 0 -> {
                    openFinalScreenStudent();
                }
            }
        }
    }

    /**
     * Method role: open the last student screen activity
     */
    private fun openFinalScreenStudent() {
        val intent = Intent(this, StudentFinalScreen::class.java)
        startActivity(intent);
    }
}