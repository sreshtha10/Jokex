/*

Description: MainActivity.kt is a launch activity responsible for handling user login and sign-ups(registrations).
It uses two fragments - LoginFragment.kt and RegisterFragment.kt

Once, the login or registration (using Firebase) is complete, the HomeActivity.kt is launched.

 */



package com.sreshtha.jokex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_Jokex)
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        init()

    }


    private fun init(){
        // set up log in fragment first at start of the Main activity

        if(checkedIfLoggedIn()){
            Intent(this,HomeActivity::class.java).also{
                startActivity(it)
                finish()
            }
        }

        val loginFragment = LoginFragment()
        setFragments(loginFragment)

    }


    private fun setFragments(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragments,fragment)
            commit()
        }
    }



    private fun checkedIfLoggedIn():Boolean{
        return auth.currentUser != null
    }




}