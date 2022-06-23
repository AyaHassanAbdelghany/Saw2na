package com.example.mcommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentContianerBinding

class FragmentContainer : Fragment() {

    private lateinit var binding: FragmentContianerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContianerBinding.inflate(inflater, container, false)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment?
        val navController = navHostFragment!!.navController
        val navGraph = navController.navInflater.inflate(R.navigation.home_navigation)
        navGraph.setStartDestination(R.id.navigation_home)
        navController.graph = navGraph

        return binding.root
    }
}