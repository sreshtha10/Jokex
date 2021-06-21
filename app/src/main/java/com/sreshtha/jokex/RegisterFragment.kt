package com.sreshtha.jokex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class RegisterFragment:Fragment(){



    lateinit var auth :FirebaseAuth
    lateinit var mainActivity: MainActivity
    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        mainActivity = activity as MainActivity
        auth = mainActivity.auth

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


        binding.btnRegister.setOnClickListener {
            registerUser()
            if(checkedIfLoggedIn()){
                Intent(context,ReadJokexActivity::class.java).also{
                    startActivity(it)
                    mainActivity.finish()
                }
            }

        }



    }


    private fun registerUser(){
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        val confirmPassword= binding.etConfirmPasswordRegister.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password.equals(confirmPassword)){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.createUserWithEmailAndPassword(email,password).await()
                    withContext(Dispatchers.Main){
                        if(checkedIfLoggedIn()){

                        }
                        else{
                            Toast.makeText(
                                context,
                                "Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        else if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){

            Toast.makeText(
                context,
                "Empty fields !",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            Toast.makeText(
                context,
                "Passwords do not match !",
                Toast.LENGTH_SHORT
            ).show()
        }
    }




    private fun checkedIfLoggedIn():Boolean{
        return auth.currentUser != null
    }








}