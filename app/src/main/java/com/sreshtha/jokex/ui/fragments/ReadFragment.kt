/*
    ReadFragment.kt displays jokes in a Recycler View, to the user which are fetched from the API.
 */


package com.sreshtha.jokex.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sreshtha.jokex.databinding.FragmentReadBinding
import com.sreshtha.jokex.ui.adapters.JokesAdapter
import com.sreshtha.jokex.ui.api.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException


class ReadFragment : Fragment() {

    // global variables.
    private lateinit var binding: FragmentReadBinding
    private lateinit var jokesAdapter: JokesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadBinding.inflate(inflater, container, false)
        jokesAdapter = JokesAdapter()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        fetchFromApi()


        binding.btnRefresh.setOnClickListener {
            fetchFromApi()
        }


    }


    private fun setUpRecyclerView() = binding.recyclerViewRead.apply {
        adapter = jokesAdapter
        layoutManager = LinearLayoutManager(activity)
    }


    // function to fetch data from API
    private fun fetchFromApi() {

        lifecycleScope.launchWhenCreated {
            binding.progressCircular.isVisible = true
            val response = try {
                RetrofitInstance.api.getJokes()
            } catch (e: IOException) {
                Log.e("ErrorInRetrofit", "You might not have internet connection")
                binding.progressCircular.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e("ErrorInRetrofit", "Unexpected Response")
                binding.progressCircular.isVisible = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                jokesAdapter.jokesList = response.body()!!
                binding.progressCircular.isVisible = false
            }

        }
    }
}