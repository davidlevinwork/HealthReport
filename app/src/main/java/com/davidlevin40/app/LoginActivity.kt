package com.davidlevin40.app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var fireBaseAuth: FirebaseAuth
    private lateinit var fireBaseList: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var textUsername: EditText? = findViewById(R.id.username_edit_text)
        var textPassword: EditText? = findViewById(R.id.password_edit_text)
        var buttonLogin: Button? = findViewById(R.id.login_button)
        var textViewRegister: TextView? = findViewById(R.id.register_text_view)

        fireBaseAuth = FirebaseAuth.getInstance()

        fireBaseList = FirebaseAuth.AuthStateListener {
            var fireBaseUser: FirebaseUser? = fireBaseAuth.currentUser
            if (fireBaseUser != null) {
                var i : Intent = Intent(this, WorkerMainScreen::class.java)
                startActivity(i)
            }
        }
        

        textViewRegister?.setOnClickListener {
                var registerIntent : Intent = Intent(this, RegisterActivity::class.java)
                startActivity(registerIntent)
        }

        buttonLogin?.setOnClickListener {
                var user = textUsername?.text.toString().trim()
                var pwd = textPassword?.text.toString().trim()

                if (user.isEmpty()) {
                    textUsername?.error = "Please enter your Email"
                    textUsername?.requestFocus()
                } else if (pwd.isEmpty()) {
                    textPassword?.error = "Please enter your password"
                    textPassword?.requestFocus()
                } else if (user.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(this, "Fields are empty!", Toast.LENGTH_SHORT).show()
                } else if (!(user.isEmpty() && pwd.isEmpty())) {
                    fireBaseAuth.signInWithEmailAndPassword(user, pwd).addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            if(fireBaseAuth.currentUser?.isEmailVerified!!) {
                                Toast.makeText(this,"Successfully Logged In", Toast.LENGTH_SHORT).show()
                                val moveToWorker = Intent(this, WorkerMainScreen::class.java)
                                startActivity(moveToWorker)
                            } else {
                                Toast.makeText(this,"Please verify your email address", Toast.LENGTH_LONG).show()
                                FirebaseAuth.getInstance().signOut()
                                val moveToLogin = Intent(this, HomeActivity::class.java)
                                startActivity(moveToLogin)
                            }
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, "Please Try Again!", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Error Occurred!", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onStart() {
        super.onStart()
        fireBaseAuth.addAuthStateListener(fireBaseList)
    }
}