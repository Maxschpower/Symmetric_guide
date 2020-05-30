package com.maxsch.symmetricguide.ui.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.maxsch.symmetricguide.FontSizeController
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.presentation.settings.SettingsViewModel
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by viewModel()

    private val fontSizeController: FontSizeController by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.logoutEvent.subscribe(this, ::navigateToLogin)

        settingsFont.setText(fontSizeController.getFontSize().toString())
        settingsSave.setOnClickListener {
            fontSizeController.changeFontSize(settingsFont.text.toString().toInt())
            findNavController().popBackStack()
        }
        settingsLogout.setOnClickListener {
            viewModel.logout()
        }
        settingsClearAcc.setOnClickListener {
            viewModel.deleteAccount()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_SettingsFragment_to_loginFragment)
    }
}
