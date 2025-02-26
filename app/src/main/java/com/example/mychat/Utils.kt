package com.example.mychat

import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.HashMap
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

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
        fun getTime(): String{
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date : Date = Date(System.currentTimeMillis())
            val stringdate = formatter.format(date)
            return stringdate
        }
    }

}