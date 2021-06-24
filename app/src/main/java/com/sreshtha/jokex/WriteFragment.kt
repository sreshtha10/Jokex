/*
    WriteFragment.kt allows user to post their own jokes.
 */


package com.sreshtha.jokex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.sreshtha.jokex.databinding.FragmentWriteBinding


class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root

    }




}