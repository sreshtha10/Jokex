/*

    HomeActivity.kt uses two fragments - ReadFragment.kt and WriteFragment.kt

 */




package com.sreshtha.jokex


import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sreshtha.jokex.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {


    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        drawer = binding.drawerLayout
        setContentView(view)


        init()

        val switch = binding.navView.menu.findItem(R.id.nav_theme).actionView as SwitchCompat
        switch.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val readFragment = ReadFragment()
        val writeFragment = WriteFragment()

        binding.navView.menu.findItem(R.id.nav_read).setOnMenuItemClickListener {

            setFragments(readFragment)
            true
        }


        binding.navView.menu.findItem(R.id.nav_write).setOnMenuItemClickListener {

            setFragments(writeFragment)
            true
        }


        binding.navView.menu.findItem(R.id.nav_signout).setOnMenuItemClickListener {

            FirebaseAuth.getInstance().signOut()
            Intent(this,MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            true
        }

    }



    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }


    private fun init(){
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val readFragment = ReadFragment()
        setFragments(readFragment)


        when (this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                (binding.navView.menu.findItem(R.id.nav_theme).actionView as SwitchCompat).isChecked = true
            }
        }


    }


    private fun setFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_home,fragment)
            commit()
        }
    }







}