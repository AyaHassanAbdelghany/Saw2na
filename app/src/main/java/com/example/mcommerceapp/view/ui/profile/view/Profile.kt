package com.example.mcommerceapp.view.ui.profile.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.R
import com.example.mcommerceapp.databinding.ActivitySigninBinding
import com.example.mcommerceapp.databinding.FragmentProfileBinding
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.profile.view_model.ProfileViewModel
import com.example.mcommerceapp.view.ui.profile.view_model.factory.ProfileViewModelFactory


class Profile : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding = FragmentProfileBinding.inflate(layoutInflater)
//        requireActivity().setContentView(binding.root)

        val viewModelFactory = ProfileViewModelFactory(UserRepo.getInstance( requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)))
        viewModel = ViewModelProvider(this,viewModelFactory)[ProfileViewModel::class.java]


        binding.profileSigninButton.setOnClickListener{
            startActivity(Intent(requireContext(),SigninActivity::class.java))
        }

        val user = viewModel.getUser()

        binding.displayNameTextView.text = user.displayName
        binding.userEmailTextView.text = user.email


    }


    override fun onResume() {
        super.onResume()

        var loggedIn = viewModel.getLoggedInState()
        //loggedIn = true

        Log.i("TAG", "onViewCreated: logged = $loggedIn ")
        when {
            loggedIn -> {
                binding.notLoggedInContainer.visibility = View.INVISIBLE
                binding.loggedInContainer.visibility = View.VISIBLE
            }
            else -> {
                binding.notLoggedInContainer.visibility = View.VISIBLE
                binding.loggedInContainer.visibility = View.INVISIBLE
            }
        }

    }

}