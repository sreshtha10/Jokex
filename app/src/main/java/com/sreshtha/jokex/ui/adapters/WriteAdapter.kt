/*
    Adapter for recycler view in the write fragment layout.
 */

package com.sreshtha.jokex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sreshtha.jokex.ui.models.UserJoke
import com.sreshtha.jokex.databinding.ItemWriteJokeBinding

class WriteAdapter(val jokesList: MutableList<UserJoke>) : RecyclerView.Adapter<WriteAdapter.WriteViewHolder>() {


    private var readRef: DatabaseReference =
        Firebase.database("https://jokex-35a23-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("user_jokes/${FirebaseAuth.getInstance().currentUser?.uid}")


    inner class WriteViewHolder(val binding:ItemWriteJokeBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<UserJoke>() {
        override fun areItemsTheSame(oldItem: UserJoke, newItem: UserJoke): Boolean {
            return oldItem.setup == newItem.setup

        }

        override fun areContentsTheSame(oldItem: UserJoke, newItem: UserJoke): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WriteViewHolder {
        return WriteViewHolder(
            ItemWriteJokeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WriteViewHolder, position: Int) {

        holder.binding.apply {
            val joke = jokesList[position]
            tvItemWriteJokeSetup.text = joke.setup
            tvItemWriteJokePunchline.text = joke.punchline

            // deleting data from recycler view plus the database when delete icon is clicked.
            ivDeleteJoke.setOnClickListener {
                readRef.child(joke.jokeId).removeValue()
                val pos = jokesList.indexOf(joke)
                jokesList.removeAt(pos)

                this@WriteAdapter.notifyItemRemoved(pos)
                this@WriteAdapter.notifyItemRangeChanged(pos,jokesList.size)



            }
        }
    }

    override fun getItemCount(): Int {
        return jokesList.size
    }



}