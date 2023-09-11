package com.example.metroyab.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.metroyab.R
import com.example.metroyab.databinding.FragmentTestBinding


class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_test, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.zoomLinearLayout.setOnTouchListener(OnTouchListener { v, event ->
            binding.zoomLinearLayout.init(requireContext())
            false
        })
    }

}


