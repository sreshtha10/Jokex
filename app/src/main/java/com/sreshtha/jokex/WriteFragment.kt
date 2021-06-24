/*
    WriteFragment.kt allows user to post their own jokes.
 */


package com.sreshtha.jokex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sreshtha.jokex.databinding.FragmentWriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding


    private val database = Firebase.database("https://jokex-35a23-default-rtdb.asia-southeast1.firebasedatabase.app/").reference



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnAddJoke.setOnClickListener {

            val setup = binding.etJokeSetup.text.toString()
            val punchline = binding.etJokePunchline.text.toString()
            addJoke(setup,punchline)
        }
    }




    private fun addJoke(setup:String,punchline:String){
        val joke = UserJoke(setup,punchline)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        var msg = "failed"



        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.child("user_jokes/$uid").push().setValue(joke)
                    .addOnSuccessListener {
                        msg = "success"
                    }
                    .addOnFailureListener {
                        msg = it.message.toString()
                    }
                    .await()


                withContext(Dispatchers.Main){
                    Toast.makeText(
                        activity,
                        msg,
                        Toast.LENGTH_LONG
                    ).show()
                    
                }


            }
            catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        activity,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }


}