package com.example.mychat.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ChatAppViewmodel : ViewModel() {
    val name = MutableLiveData<String> ()
    val imageUrl= MutableLiveData<String>()
    val message = MutableLiveData<String>()
    init{
    }
}