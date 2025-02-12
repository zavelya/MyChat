package com.example.mychat.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mychat.R
import com.example.mychat.databinding.FragmentSettingBinding
import com.example.mychat.mvvm.ChatAppViewmodel

class  SettingFragment : Fragment() {
    lateinit var viewModel: ChatAppViewmodel
    lateinit var binding : FragmentSettingBinding

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          //_binding = FragmentSettingsBinding.inflate(inflater, container, false)
          binding.lifecycleOwner = viewLifecycleOwner // ✅ LiveData'nın XML ile çalışmasını sağlar
          binding.viewModel = viewModel // ✅ XML dosyasında tanımlanan ViewModel'e bağlama yapıldı

          return binding.root
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

}