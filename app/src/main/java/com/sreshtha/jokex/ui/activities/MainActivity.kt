/*

Description: MainActivity.kt is a launch activity responsible for handling user login and sign-ups(registrations).
It uses two fragments - LoginFragment.kt and RegisterFragment.kt

Once, the login or registration (using Firebase) is complete, the HomeActivity.kt is launched.

 */



package com.sreshtha.jokex.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.ui.fragments.LoginFragment
import com.sreshtha.jokex.R
import com.sreshtha.jokex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    //global variables
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //setting theme to Theme.Jokex to end splash screen.
        setTheme(R.style.Theme_Jokex)
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        init()

    }


    private fun init() {
        // if user is already logged in, start the home activity directly.
        if (checkedIfLoggedIn()) {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        //else displaying the login fragment in main activity
        val loginFragment = LoginFragment()
        setFragments(loginFragment)

    }


    //function to switch between register and login fragment in main activity
    private fun setFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragments, fragment)
            commit()
        }
    }


    //function to check the logged in state of the user.
    private fun checkedIfLoggedIn(): Boolean {
        return auth.currentUser != null
    }


}