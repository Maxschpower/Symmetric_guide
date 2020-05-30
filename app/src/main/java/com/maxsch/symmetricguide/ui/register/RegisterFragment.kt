package com.maxsch.symmetricguide.ui.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.presentation.register.RegisterViewModel
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginEvent.subscribe(this, ::login)

        registerButton.setOnClickListener {
            viewModel.register(registerUsername.text.toString(), registerPassword.text.toString())
        }
    }

    private fun login() {
        findNavController().navigate(R.id.action_registerFragment_to_MaterialsFragment)
    }
}