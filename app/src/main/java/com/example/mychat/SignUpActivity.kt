package com.example.mychat

import android.app.ProgressDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.mychat.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var firestore : FirebaseFirestore
    private lateinit var signUpAuth : FirebaseAuth
    private lateinit var name : String
    private lateinit var email : String
    private lateinit var password :String
    private lateinit var signUpPd: ProgressDialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setContentView(R.layout.activity_sign_up)
        firestore = FirebaseFirestore.getInstance()
        signUpAuth= FirebaseAuth.getInstance()
        signUpPd= ProgressDialog(this )


        signUpBinding.signUpTextToSignIn.setOnClickListener{

        }
    }
}