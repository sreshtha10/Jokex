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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

    private val jokeList: MutableList<UserJoke> = mutableListOf()

    private val database = Firebase.database("https://jokex-35a23-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

    private lateinit var readRef: DatabaseReference

    private lateinit var writeAdapter: WriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        readRef =
            Firebase.database("https://jokex-35a23-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("user_jokes/${FirebaseAuth.getInstance().currentUser?.uid}")


        binding = FragmentWriteBinding.inflate(inflater, container, false)

        writeAdapter = WriteAdapter(jokeList)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        readRef.addValueEventListener(readJokesListener)

        setUpRecyclerView()


        binding.btnAddJoke.setOnClickListener {

            val setup = binding.etJokeSetup.text.toString()
            val punchline = binding.etJokePunchline.text.toString()
            writeJokeToCloud(setup, punchline)

        }
    }


    private fun writeJokeToCloud(setup: String, punchline: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        var msg = "failed"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jokeId = database.child("user_jokes/$uid").push().key
                val joke = UserJoke(jokeId!!,setup, punchline)



                database.child("user_jokes/$uid/${joke.jokeId}").setValue(joke)
                    .addOnSuccessListener {
                        msg = "success"
                    }
                    .addOnFailureListener {
                        msg = it.message.toString()
                    }
                    .await()


                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity,
                        msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private val readJokesListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            dataSnapshot.children.forEach {
                val key = it.key
                val joke = dataSnapshot.child("$key").getValue(UserJoke::class.java)

                if(joke != null){
                    jokeList.add(joke)
                    writeAdapter.notifyItemInserted(jokeList.size-1)
                }

            }
        }

        override fun onCancelled(error: DatabaseError) {

        }

    }



    private fun setUpRecyclerView() = binding.rvMyJokes.apply {
        adapter = writeAdapter
        layoutManager = LinearLayoutManager(activity)
    }



}