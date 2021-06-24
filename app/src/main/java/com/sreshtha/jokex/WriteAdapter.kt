package com.sreshtha.jokex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sreshtha.jokex.databinding.ItemWriteJokeBinding

class WriteAdapter(val jokesList: MutableList<UserJoke>) : RecyclerView.Adapter<WriteAdapter.WriteViewHolder>() {

    inner class WriteViewHolder(val binding:ItemWriteJokeBinding):RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<UserJoke>() {
        override fun areItemsTheSame(oldItem: UserJoke, newItem:UserJoke): Boolean {
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
        }
    }

    override fun getItemCount(): Int {
        return jokesList.size
    }


}