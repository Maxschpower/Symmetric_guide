package com.maxsch.symmetricguide.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.presentation.login.LoginViewModel
import com.maxsch.symmetricguide.presentation.login.ScreenState
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginPassedEvent.subscribe(this, ::openMaterialsListScreen)
        viewModel.screenState.subscribe(this, ::applyScreenState)

        loginButton.setOnClickListener {
            viewModel.login(
                loginUsernameEditText.text.toString(),
                loginPasswordEditText.text.toString()
            )
        }

        loginRegisterButton.setOnClickListener {
            openRegisterScreen()
        }
    }

    private fun openMaterialsListScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_MaterialsFragment)
    }

    private fun applyScreenState(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Error.WrongPassword -> {
                registerPasswordLayout.isErrorEnabled = true
                registerPasswordLayout.error = screenState.errorText
            }
            is ScreenState.Error.WrongUsername -> {
                registerUsernameLayout.isErrorEnabled = true
                registerUsernameLayout.error = screenState.errorText
                loginUsernameEditText.setText("")
            }
            is ScreenState.Default -> {
                registerUsernameLayout.isErrorEnabled = false
            }
        }
    }

    private fun openRegisterScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
    }
}