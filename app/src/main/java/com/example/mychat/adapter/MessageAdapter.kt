package com.example.mychat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mychat.R
import com.example.mychat.Utils
import com.example.mychat.modal.Messages

class MessageAdapter: RecyclerView.Adapter<MessageHolder>() {
    private var listOfMessage= listOf<Messages>() as List<Messages>
    private val LEFT = 0
    private val RIGHT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if ( viewType == RIGHT ){
            val view = inflater.inflate(R.layout.chatitemright, parent,false )
            MessageHolder(view)
        }else{
            val view=inflater.inflate(R.layout.chatitemleft,parent,false)
            MessageHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listOfMessage.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message = listOfMessage[position] as Messages
        holder.messageText.visibility=View.VISIBLE
        holder.messageText.visibility=View.VISIBLE
        holder.messageText.setText(message.message)
        holder.timeOfSent.text= message.time?.substring(0,5)?:""

    }

    override fun getItemViewType(position: Int) =
        if(listOfMessage[position].sender== Utils.getUiLoggedIn()) RIGHT else LEFT

        fun setMessageList(newList: List<Messages>){
            this.listOfMessage=newList
        }
}

class MessageHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
    val messageText : TextView = itemView.findViewById(R.id.show_message)
    val timeOfSent : TextView = itemView.findViewById(R.id.timeView)
}