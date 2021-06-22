/*
    LoginFragment.kt is responsible for handling user-logins using FirebaseAuth.
    It is a fragment created for MainActivity.kt

 */


package com.sreshtha.jokex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception


class LoginFragment:Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity
        auth = mainActivity.auth
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvGotoSignup.setOnClickListener {
            val registerFragment = RegisterFragment()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, registerFragment)
                commit()
            }
        }


        binding.btnLogin.setOnClickListener {
            logInUser()
        }


    }


    private fun logInUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {

                    auth.signInWithEmailAndPassword(email, password).await()

                    withContext(Dispatchers.Main) {
                        if (checkedIfLoggedIn()) {
                            Toast.makeText(
                                context,
                                "Success",
                                Toast.LENGTH_SHORT
                            ).show()

                            Intent(context, HomeActivity::class.java).also {
                                startActivity(it)
                                mainActivity.finish()
                            }
                        }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }


        }


    }


    private fun checkedIfLoggedIn(): Boolean {
        return auth.currentUser != null
    }



}