package com.sreshtha.jokex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sreshtha.jokex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_Jokex)
        setContentView(view)

        init()


    }


    private fun init(){
        // set up log in fragment first at start of the Main activity
        val loginFragment = LoginFragment()
        setFragments(loginFragment)

    }


    private fun setFragments(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragments,fragment)
            commit()
        }
    }


}