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

        signinBinding.signInTextToSignUp.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java ))

        }
    }
}