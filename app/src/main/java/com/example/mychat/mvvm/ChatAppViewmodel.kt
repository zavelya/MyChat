package com.example.mychat.mvvm

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.chatmessenger.mvvm.ChatListRepo
import com.example.mychat.MyApplication
import com.example.mychat.SharedPrefs
import com.example.mychat.Utils
import com.example.mychat.modal.Messages
import com.example.mychat.modal.RecentChats
import com.example.mychat.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
private val firestore= FirebaseFirestore.getInstance()

class ChatAppViewModel : ViewModel() {
    val name = MutableLiveData<String> ()
    val imageUrl= MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val usersRepo = UsersRepo()
    val messagesRepo = MessageRepo()
    val recentChatRepo = ChatListRepo()

    init{
        getCurrentUser()
    }


    fun getUsers() : LiveData<List<Users>>{

        return usersRepo.getUsers()

    }
//get current user info
fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {
    val context = MyApplication.instance.applicationContext

    //val userId = Utils.getUiLoggedIn()
    /* if (userId.isNullOrEmpty()) {
        // Kullanıcı ID null veya boşsa işlemi iptal et
        return@launch
    }*/

    firestore.collection("Users").document(Utils.getUiLoggedIn())
        .addSnapshotListener { value, error ->
            if (error != null) {
                // Firestore'dan veri çekerken hata olursa logla
                return@addSnapshotListener
            }

            if (value != null && value.exists()) {
                val users = value.toObject(Users::class.java)

                // Null değerleri kontrol et ve varsayılan değer ata
                name.value=users?.username!!
                imageUrl.value= users?.imageUrl!!

                val mysharedPrefs = SharedPrefs(context)
                mysharedPrefs.setValue("username", users.username!! )
            }
        }
}
    @SuppressLint("SuspiciousIndentation")
    fun sendMessage(sender: String, receiver: String?, friendname: String, friendimage: String) = viewModelScope.launch(Dispatchers.IO) {
        val context = MyApplication.instance.applicationContext

        if (receiver.isNullOrEmpty()) {
            Log.e("ChatAppViewModel", "Receiver ID is null!")
            return@launch
        }

        val messageText = message.value ?: return@launch
        val senderName = name.value ?: "Unknown"

        val hashMap = hashMapOf<String, Any>(
            "sender" to sender,
            "receiver" to receiver,
            "message" to messageText,
            "time" to Utils.getTime()
        )

        val uniqueId = listOf(sender, receiver).sorted().joinToString("")

        firestore.collection("Messages").document(uniqueId)
            .collection("chats").document(Utils.getTime()).set(hashMap)
            .addOnCompleteListener { task ->
                val hashMapForRecent = hashMapOf<String, Any>(
                    "friendid" to receiver,
                    "time" to Utils.getTime(),
                    "sender" to Utils.getUiLoggedIn(),
                    "message" to messageText,
                    "friendsimage" to friendimage,
                    "name" to friendname,
                    "person" to "you"
                )

                firestore.collection("Conversation${Utils.getUiLoggedIn()}").document(receiver)
                    .set(hashMapForRecent, SetOptions.merge())

                firestore.collection("Conversation$receiver").document(Utils.getUiLoggedIn())
                    .set(mapOf(
                        "message" to messageText,
                        "time" to Utils.getTime(),
                        "person" to senderName
                    ), SetOptions.merge())

                if (task.isSuccessful) {
                    message.postValue("")
                }
            }
    }

//SEND MESSAGE
/*@SuppressLint("SuspiciousIndentation")
fun sendMessage(sender: String, receiver : String, friendname: String, friendimage:String)=viewModelScope.launch(Dispatchers.IO) {
    val context = MyApplication.instance.applicationContext
    val hashMap= hashMapOf<String, Any>(
        "sender" to sender, "receiver" to receiver , "message" to message.value!!, "time" to Utils.getTime()
    )

    val uniqueId= listOf(sender,receiver).sorted()
    uniqueId.joinToString (separator="")
    val friendnamesplit= friendname.split("\\s".toRegex())[0]

    val mysharedPrefs= SharedPrefs(context)
    mysharedPrefs.setValue("friendid", receiver)
    mysharedPrefs.setValue("chatroomid", uniqueId.toString())
    mysharedPrefs.setValue("friendid", friendnamesplit)
    mysharedPrefs.setValue("friendimage", friendimage)
    //sending message
    firestore.collection("Messages").document(uniqueId.toString())
        .collection("chats").document(Utils.getTime()).set(hashMap).addOnCompleteListener{ task->
        val hashMapForRecent = hashMapOf<String, Any>(
            "friendid" to receiver ,
            "time" to Utils.getTime(), "sender" to Utils.getUiLoggedIn(), "message" to message.value!!,
            "friendsimage" to friendimage  , "name" to friendname, "person" to "you"        )

            firestore.collection("Conversation${Utils.getUiLoggedIn()}").document(receiver).set(hashMapForRecent)

            firestore.collection("Conversation${receiver}").document(Utils.getUiLoggedIn()).update("message", message.value!!, "time", Utils.getTime(), "person", name.value!!)

            if(task.isSuccessful){
                message.value=""
            }



        }
}*/
 fun getMessages(friendid: String) : LiveData<List<Messages>>{
     return messagesRepo.getMessages(friendid)
 }
    fun getRecentChats():LiveData<List<RecentChats>>{
        return recentChatRepo.getAllChatList()
    }
}

