package com.example.mychat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychat.R
import de.hdodenhof.circleimageview.CircleImageView
import com.bumptech.glide.Glide
import com.example.mychat.modal.Users

class UserAdapter : RecyclerView.Adapter<UserHolder>() {
    // Değiştirilen liste tipini MutableList<Users> olarak tanımlıyoruz
    private var listOfUsers = mutableListOf<Users>()
    private var listener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.userlistitem, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val users = listOfUsers[position]

        val name = users.username!!.split("\\s".toRegex())[0]

        holder.profileName.text = name

        if (users.status == "Online") {
            holder.statusImageView.setImageResource(R.drawable.onlinestatus)
        } else {
            holder.statusImageView.setImageResource(R.drawable.offlinestatus)
        }

        Glide.with(holder.itemView.context).load(users.imageUrl).into(holder.imageProfile)

        holder.itemView.setOnClickListener {
            listener?.onUserSelected(position, users)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(list: List<Users>) {
        // Mutable liste olduğunda set edebiliyoruz
        listOfUsers.clear() // Önceki verileri temizle
        listOfUsers.addAll(list) // Yeni verileri ekle
        notifyDataSetChanged()
    }

    fun setOnUserClickListener(listener: OnUserClickListener) {
        this.listener = listener
    }
}

class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val profileName: TextView = itemView.findViewById(R.id.userName)
    val imageProfile: CircleImageView = itemView.findViewById(R.id.imageViewUser)
    val statusImageView: ImageView = itemView.findViewById(R.id.statusOnline)
}

interface OnUserClickListener {
    fun onUserSelected(position: Int, users: Users)
}
