package com.miqbalkalevi.joggingtracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.miqbalkalevi.joggingtracker.R
import com.miqbalkalevi.joggingtracker.databinding.FragmentLoginBinding
import com.miqbalkalevi.joggingtracker.exhaustive
import com.miqbalkalevi.joggingtracker.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect { event ->
                when (event) {
                    is LoginViewModel.LoginEvent.NavigateToMainScreen -> {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }.exhaustive
            }
        }

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
                viewModel.onStartButtonClick()
            }
        }

    }

}