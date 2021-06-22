
/*
    Adapter for Recycler View
 */

package com.sreshtha.jokex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sreshtha.jokex.databinding.ItemReadJokeBinding

class JokesAdapter:RecyclerView.Adapter<JokesAdapter.JokesViewHolder>() {

    inner class JokesViewHolder(val binding:ItemReadJokeBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallBack = object : DiffUtil.ItemCallback<JokeData>(){
        override fun areItemsTheSame(oldItem: JokeData, newItem: JokeData): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: JokeData, newItem: JokeData): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)


    var jokesList : List<JokeData>
        get() = differ.currentList
        set(value) {differ.submitList(value)}






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        return JokesViewHolder(ItemReadJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.binding.apply {
            val joke = jokesList[position]
            tvItemReadJokeSetup.text = joke.setup
            tvItemReadJokePickup.text = joke.punchline
        }
    }

    override fun getItemCount(): Int {
        return jokesList.size

    }
}