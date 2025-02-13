@file:Suppress("DEPRECATION")

package com.example.mychat

import android.app.ProgressDialog
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.content.Intent
import android.widget.TextView
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.example.mychat.MainActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mychat.R
import com.example.mychat.databinding.ActivitySignInBinding
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class SignInActivity : AppCompatActivity() {
    // private lateinit var  goToSignUpActivity : TextView
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var auth : FirebaseAuth
    private lateinit var progressDialogSignIn: ProgressDialog
    private lateinit var signinBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signinBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }

        progressDialogSignIn= ProgressDialog(this)

        signinBinding.signInTextToSignUp.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java ))

        }
        signinBinding.loginButton.setOnClickListener{
            email=signinBinding.loginetemail.text.toString()
            password= signinBinding.loginetpassword.text.toString()

            if(signinBinding.loginetemail.text.isEmpty())
            {
                Toast.makeText(this, "Empty cant be empty", Toast.LENGTH_SHORT).show()
            }
        }
        if(signinBinding.loginetpassword.text.isEmpty()){
            Toast.makeText(this, "Password cant be empty", Toast.LENGTH_SHORT).show()
        }
        if(signinBinding.loginetpassword.text.isEmpty() && signinBinding.loginetemail.text.isNotEmpty()){
            signIn(password,email)
        }
    }

    private fun signIn(password: String, email: String) {
        progressDialogSignIn.show()
        progressDialogSignIn.setMessage("Signing In")

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
     if(it.isSuccessful){
        progressDialogSignIn.dismiss()
        startActivity(Intent(this, MainActivity::class.java))
    } else{
    progressDialogSignIn.dismiss()
Toast.makeText(this, "Invalid credentials",Toast.LENGTH_SHORT).show()
}
}.addOnFailureListener{exception->
when(exception ){
is FirebaseAuthInvalidCredentialsException->{
Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show()
}
 }}}

    override fun onBackPressed() {
        super.onBackPressed()
progressDialogSignIn.dismiss()
finish()
}