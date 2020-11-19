package com.davidlevin40.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.api.AnnotationsProto.http
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var textUsername: EditText? = findViewById(R.id.username_edit_text)
        var textPassword: EditText? = findViewById(R.id.password_edit_text)
        var textCnfPassword: EditText? = findViewById(R.id.repassword_edit_text)
        var buttonRegister: Button? = findViewById(R.id.register_button)
        var textViewLogin: TextView? = findViewById(R.id.login_text_view)
        var fab : FloatingActionButton? = findViewById(R.id.info)
        var termsOfService : TextView? = findViewById(R.id.terms_of_service)

        var fireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance();

        fab?.setOnClickListener{
            Toast.makeText(this, "Your password must contains at least 6 characters", Toast.LENGTH_LONG).show()
        }

        textViewLogin?.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }

        termsOfService?.setOnClickListener{
            val url : String = "https://www.websitepolicies.com/policies/view/S6x1oPYK"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        buttonRegister?.setOnClickListener {
            val user = textUsername?.text.toString().trim()
            val pwd = textPassword?.text.toString().trim()
            val cnfPwd = textCnfPassword?.text.toString().trim()

            if (user.isEmpty()) {
                textUsername?.error = "Please enter username"
                textUsername?.requestFocus()
            } else if (pwd.isEmpty() || cnfPwd.isEmpty()) {
                textPassword?.error = "Please enter your password"
                textPassword?.requestFocus()
            } else if (user.isEmpty() && pwd.isEmpty() && cnfPwd.isEmpty()) {
                Toast.makeText(this, "Fields are empty!", Toast.LENGTH_LONG).show()
            } else if (!(user.isEmpty() && pwd.isEmpty() && cnfPwd.isEmpty())) {
                fireBaseAuth.createUserWithEmailAndPassword(user, pwd).addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        fireBaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener(
                            OnCompleteListener {
                                if(task.isSuccessful) {
                                    Toast.makeText(this,"Registered successfully", Toast.LENGTH_SHORT).show()
                                    Toast.makeText(this, "Please check your email for verification", Toast.LENGTH_LONG).show()
                                    FirebaseAuth.getInstance().signOut()
                                    val moveToLogin = Intent(this, HomeActivity::class.java)
                                    startActivity(moveToLogin)
                                } else {
                                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                                }
                            })
                    } else {
                        Toast.makeText(this, "Registration Error", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Error Occurred!", Toast.LENGTH_LONG).show()
            }
        }
    }
}