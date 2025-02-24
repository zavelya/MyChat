package com.example.mychat.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.mychat.MyApplication
import com.example.mychat.SharedPrefs
import com.example.mychat.Utils
import com.example.mychat.modal.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
private val firestore= FirebaseFirestore.getInstance()

class ChatAppViewModel : ViewModel() {
    val name = MutableLiveData<String> ()
    val imageUrl= MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val usersRepo = UsersRepo()

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

}
/*fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {
    val context = MyApplication.instance.applicationContext
    val userId = Utils.getUiLoggedIn()

    if (userId.isNullOrEmpty()) {
        // Eğer kullanıcı ID null veya boşsa işlemi iptal et
        return@launch
    }

    firestore.collection("Users").document(userId)
        .addSnapshotListener { value, error ->
            if (error != null) {
                // Firestore'dan veri çekerken hata olduysa logla
                return@addSnapshotListener
            }

            if (value != null && value.exists()) {
                val users = value.toObject(Users::class.java)

                // null kontrolü ekleyerek LiveData güncelle
                name.postValue(users?.username ?: "")
                imageUrl.postValue(users?.imageUrl ?: "")

                val mysharedPrefs = SharedPrefs(context)
                mysharedPrefs.setValue("username", users?.username ?: "")
            }
        }}}*/
