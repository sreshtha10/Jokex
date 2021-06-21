package com.sreshtha.jokex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.databinding.FragmentLoginBinding


class LoginFragment:Fragment(){

    private lateinit var binding: FragmentLoginBinding
    private lateinit var  mainActivity: MainActivity
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container,false)
        mainActivity = activity as MainActivity
        auth = mainActivity.auth
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvGotoSignup.setOnClickListener {
            val registerFragment = RegisterFragment()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments,registerFragment)
                commit()
            }
        }


        binding.btnLogin.setOnClickListener {

        }


    }









}