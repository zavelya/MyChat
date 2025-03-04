package com.example.mychat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mychat.R
import com.example.mychat.modal.RecentChats
import de.hdodenhof.circleimageview.CircleImageView

class RecentChatAdapter : RecyclerView.Adapter<RecentChatHolder>() {
    private var listofchats = mutableListOf<RecentChats>()
    private var listener: onRecentChatClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recentchatlist, parent, false)
        return RecentChatHolder(view)
    }

    override fun getItemCount(): Int {
        return listofchats.size
    }

    override fun onBindViewHolder(holder: RecentChatHolder, position: Int) {
        val recentChat = listofchats[position]

        holder.userName.text = recentChat.name

        // Mesajı düzgün şekilde kısalt
        val shortenedMessage = recentChat.message?.take(10) ?: "Mesaj yok"
        val formattedMessage = "${recentChat.person}: $shortenedMessage"
        holder.lastMessage.text = formattedMessage

        Glide.with(holder.itemView.context)
            .load(recentChat.friendsimage)
            .into(holder.imageView)
        holder.timeView.text = recentChat.time?.substring(0, 5) ?: ""

        holder.itemView.setOnClickListener {
            listener?.getOnRecentChatClicked(position, recentChat)
        }
    }

    fun setOnRecentChatListener(listener: onRecentChatClicked) {
        this.listener = listener
    }

    fun setOnRecentList(list: List<RecentChats>) {
        listofchats.clear()
        listofchats.addAll(list)
        notifyDataSetChanged()
    }
}

class RecentChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: CircleImageView = itemView.findViewById(R.id.recentChatImageView)
    val userName: TextView = itemView.findViewById(R.id.recentChatTextName)
    val lastMessage: TextView = itemView.findViewById(R.id.recentChatTextLastMessage)
    val timeView: TextView = itemView.findViewById(R.id.recentChatTextTime)
}

interface onRecentChatClicked {
    fun getOnRecentChatClicked(position: Int, recentChat: RecentChats)
}
