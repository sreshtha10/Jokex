package com.sreshtha.jokex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sreshtha.jokex.databinding.ActivityReadJokexBinding

class ReadJokexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadJokexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadJokexBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_Jokex)
        setContentView(view)

    }
}