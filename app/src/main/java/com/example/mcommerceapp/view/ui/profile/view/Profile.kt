package com.example.mcommerceapp.view.ui.profile.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentProfileBinding
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.authentication.signin.view.SigninActivity
import com.example.mcommerceapp.view.ui.authentication.signup.view.SignUpActivity
import com.example.mcommerceapp.view.ui.favorite_product.view.FavoriteScreen
import com.example.mcommerceapp.view.ui.order.OrderActivity
import com.example.mcommerceapp.view.ui.profile.view_model.ProfileViewModel
import com.example.mcommerceapp.view.ui.profile.view_model.factory.ProfileViewModelFactory
import com.example.mcommerceapp.view.ui.shopping_cart.view.ShoppingCartScreen


class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = ProfileViewModelFactory(UserRepo.getInstance(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        binding.signInConstraint.setOnClickListener {
            startActivity(Intent(requireContext(), SigninActivity::class.java))
        }

        binding.signUpConstraint.setOnClickListener {
            startActivity(Intent(requireContext(), SignUpActivity::class.java))
        }

        binding.linearLayout.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    OrderActivity::class.java
                )
            )
        }


        binding.linearLayout3.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    FavoriteScreen::class.java
                )
            )
        }

        binding.linearLayout2.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    ShoppingCartScreen::class.java
                )
            )
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.retrieveUserFromFireStore().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.displayNameTextView.text = it.displayName
                binding.userEmailTextView.text = it.email
                Log.i("user", "onViewCreated: $it")
            }
        }

        val loggedIn = viewModel.getLoggedInState()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}