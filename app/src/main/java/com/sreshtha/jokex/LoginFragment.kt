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


class LoginFragment : Fragment() {

    //global variables
    private lateinit var binding: FragmentLoginBinding
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // getting firebase auth instance
        auth = FirebaseAuth.getInstance()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // this switches to sign up (register) fragment
        binding.tvGotoSignup.setOnClickListener {
            val registerFragment = RegisterFragment()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragments, registerFragment)
                commit()
            }
        }

        // getting user logged in
        binding.btnLogin.setOnClickListener {
            logInUser()
        }


    }


    // function to handle the log in of the user
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

                            //on successful log in, starting the home activity
                            Intent(context, HomeActivity::class.java).also {
                                startActivity(it)
                                activity?.finish()
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


        } else {
            Toast.makeText(
                activity,
                "Empty Fields",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    //function to the check the logged in state of the user.
    private fun checkedIfLoggedIn(): Boolean {
        return auth.currentUser != null
    }


}