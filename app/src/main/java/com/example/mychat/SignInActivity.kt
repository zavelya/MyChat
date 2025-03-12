@file:Suppress("DEPRECATION")

package com.example.mychat

import android.app.ProgressDialog
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mychat.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class SignInActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialogSignIn: ProgressDialog
    private lateinit var signinBinding: ActivitySignInBinding
    private lateinit var firebaseAnalytics : FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        signinBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        firebaseAnalytics = Firebase.analytics
        auth = FirebaseAuth.getInstance()

        // If user is already signed in, redirect to MainActivity
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        progressDialogSignIn = ProgressDialog(this)

        // SignUp redirection
        signinBinding.signInTextToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Login button click listener
        signinBinding.loginButton.setOnClickListener {

            email = signinBinding.loginetemail.text.toString()
            password = signinBinding.loginetpassword.text.toString()

            // Email and password validation
            if (email.isEmpty()) {
                Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call signIn function if all fields are filled
            signIn(password, email)
        }
    }


    private fun signIn(password: String, email: String) {
        progressDialogSignIn.show()
        progressDialogSignIn.setMessage("Signing In")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            progressDialogSignIn.dismiss()
            if (task.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            progressDialogSignIn.dismiss()
            when (exception) {
                is FirebaseAuthInvalidCredentialsException -> {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Auth Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    // Handling back press to dismiss progress dialog and finish activity
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        progressDialogSignIn.dismiss()
        finish()
    }

    // Dismissing progress dialog when activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        progressDialogSignIn.dismiss()
    }
}
