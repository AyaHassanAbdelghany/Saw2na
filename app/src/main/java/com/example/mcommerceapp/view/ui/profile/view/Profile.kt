package com.example.mcommerceapp.view.ui.profile.view

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.FragmentProfileBinding


class Profile : Fragment() {

lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notLoggedInContainer.visibility = View.INVISIBLE
        binding.loggedInContainer.visibility = View.VISIBLE


    }

}