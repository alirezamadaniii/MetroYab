package com.example.metroyab.presentation.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.metroyab.R
import com.example.metroyab.data.utlis.onBackPressed
import com.example.metroyab.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnRouting.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_routingFragment)
        }

        binding.btnNearestMetro.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

    }

}