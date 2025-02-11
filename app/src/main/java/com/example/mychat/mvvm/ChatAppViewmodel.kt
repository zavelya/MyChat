package com.example.mychat.mvvm

import androidx.lifecycle.MutableLiveData

class ChatAppViewmodel : ViewModel() {
    val name = MutableLiveData<String> ()
    val imageUrl= MutableLiveData<String>()
    val message = MutableLiveData<String>()
}