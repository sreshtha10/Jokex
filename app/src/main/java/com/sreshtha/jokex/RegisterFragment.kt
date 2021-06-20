package com.sreshtha.jokex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sreshtha.jokex.databinding.FragmentRegisterBinding


class RegisterFragment:Fragment(){

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginFragment = LoginFragment()

        binding.tvGotoLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments,loginFragment)
                commit()
            }
        }

    }


}