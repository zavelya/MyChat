package com.example.mychat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.mychat.R
import com.example.mychat.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var args : ChatFragmentArgs
    private lateinit var chatbinding  : FragmentChatBinding
    private lateinit var chatoolbar : Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatbinding= DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return chatbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args= ChatFragmentArgs.fromBundle(requireArguments())
    }

}

class ChatFragmentArgs {

}
