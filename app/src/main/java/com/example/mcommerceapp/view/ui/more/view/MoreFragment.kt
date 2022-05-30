package com.example.mcommerceapp.view.ui.more.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mcommerceapp.databinding.FragmentMoreBinding
import com.example.mcommerceapp.model.user_repository.UserRepo
import com.example.mcommerceapp.view.ui.more.view_model.MoreViewModel
import com.example.mcommerceapp.view.ui.more.view_model.factory.MoreViewModelFactory


class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null

    private val binding get() = _binding!!
    private lateinit var viewModel: MoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = MoreViewModelFactory(UserRepo.getInstance( requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)))
        viewModel = ViewModelProvider(this,viewModelFactory)[MoreViewModel::class.java]


        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
            Toast.makeText(requireContext(), "Signed Out Successfully", Toast.LENGTH_SHORT).show()
        }

        val currencyArray = arrayOf("USD", "EUR", "EGP")
        val languagesArray = arrayOf("Arabic","English")
        val currencyAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),R.layout.simple_spinner_item, currencyArray)
        currencyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.currencySpinner.adapter = currencyAdapter

        val languageAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),R.layout.simple_spinner_item, languagesArray)
        languageAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}