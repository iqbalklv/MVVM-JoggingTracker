package com.miqbalkalevi.joggingtracker.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.miqbalkalevi.joggingtracker.R
import com.miqbalkalevi.joggingtracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        binding.apply {
            viewModel.weight?.let { etWeight.setText(it.toString()) }
            viewModel.height?.let { etHeight.setText(it.toString()) }

            etWeight.addTextChangedListener {
                if (!it.toString().isNullOrEmpty()) {
                    viewModel.weight = it.toString().toInt()
                }
                if (it != null) {
                    viewModel.onEditTextChanged(0, it.length)
                }
            }

            etHeight.addTextChangedListener {
                if (!it.toString().isNullOrEmpty()) {
                    viewModel.height = it.toString().toInt()
                }
                if (it != null) {
                    viewModel.onEditTextChanged(1, it.length)
                }
            }

            viewModel.inputCount.observe(viewLifecycleOwner) {
                btnStart.isEnabled = viewModel.isButtonValidated()
            }

            btnStart.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToRunsFragment()
                findNavController().navigate(action)
            }
        }

    }

}