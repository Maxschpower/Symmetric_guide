package com.maxsch.symmetricguide.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    fun pop() {
        findNavController().popBackStack()
    }
}