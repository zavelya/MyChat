package com.example.mychat

import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.HashMap
import android.annotation.SuppressLint
class Utils {

    companion object{
        private val auth = FirebaseAuth.getInstance()
        private var userid : String = ""

        fun getUiLoggedIn():String{

            if(auth.currentUser!=null){

                userid = auth.currentUser!!.uid

            }

            return userid





        }
    }

}