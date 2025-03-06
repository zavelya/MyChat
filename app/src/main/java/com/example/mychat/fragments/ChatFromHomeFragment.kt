package com.example.mychat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mychat.R
import com.example.mychat.Utils
import com.example.mychat.adapter.MessageAdapter
import com.example.mychat.databinding.FragmentChatBinding
import com.example.mychat.databinding.FragmentChatfromHomeBinding
import com.example.mychat.modal.Messages
import com.example.mychat.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView


class ChatFromHomeFragment : Fragment() {
    private lateinit var args : ChatFromHomeFragmentArgs
    private lateinit var chatfromhomebinding  : FragmentChatfromHomeBinding
    private lateinit var chattoolbar : androidx.appcompat.widget.Toolbar
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var circleImageView: CircleImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvStatus: TextView
    private lateinit var backbtn: ImageView
    private lateinit var messageAdapter : MessageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       chatfromhomebinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatfrom_home, container, false )
        return chatfromhomebinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args= ChatFromHomeFragmentArgs.fromBundle(requireArguments())
        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)
        chattoolbar=view.findViewById(R.id.toolBarChat)
        circleImageView=chattoolbar.findViewById(R.id.chatImageViewUser)
        tvStatus=view.findViewById(R.id.chatUserStatus)
        tvUserName= view.findViewById(R.id.chatUserName)
        backbtn = chattoolbar.findViewById(R.id.chatBackBtn)

        backbtn.setOnClickListener{
            view.findNavController().navigate(R.id.action_chatFromHomeFragment_to_homeFragment)
        }

        Glide.with(requireContext()).load(args.recentchats.friendsimage!!).into(circleImageView)
        tvStatus.setText(args.recentchats.status)
        tvUserName.setText(args.recentchats.name)




        chatfromhomebinding.chatBackBtn.setOnClickListener{
            view.findNavController().navigate(R.id.action_chatFromHomeFragment_to_homeFragment)
        }
        Glide.with(requireContext()).load(args.recentchats.friendid!!).into(circleImageView)
        chatfromhomebinding.viewModel=chatAppViewModel
        chatfromhomebinding.lifecycleOwner=viewLifecycleOwner

        chatfromhomebinding.sendBtn.setOnClickListener{
            chatAppViewModel.sendMessage(Utils.getUiLoggedIn(), args.recentchats.friendid!!, args.recentchats.name!!, args.recentchats.friendsimage!!)

        }
        chatAppViewModel.getMessages(args.recentchats.friendid!!).observe(viewLifecycleOwner, Observer{

            initRecyclerView(it)

        })


    }

    private fun initRecyclerView(it: List<Messages>?) {
        messageAdapter=MessageAdapter()
        val layoutManager = LinearLayoutManager(context)
        chatfromhomebinding.messagesRecyclerView.layoutManager = layoutManager
        layoutManager.stackFromEnd=true
        if (it != null) {
            messageAdapter.setMessageList(it)
        }
        messageAdapter.notifyDataSetChanged()
        chatfromhomebinding.messagesRecyclerView.adapter=messageAdapter
    }

}


