package com.maxsch.symmetricguide.ui.material.editor

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.presentation.materials.MaterialEditorViewModel
import com.maxsch.symmetricguide.presentation.materials.ScreenState
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_editor.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MaterialEditorFragment : BaseFragment(R.layout.fragment_editor) {

    companion object {
        const val BUNDLE_MATERIAL_ID = "id"
    }

    private val viewModel: MaterialEditorViewModel by viewModel {
        parametersOf(arguments?.getInt(BUNDLE_MATERIAL_ID, 0) ?: 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.getInt(BUNDLE_MATERIAL_ID, 0) ?: 0 == 0)
            title.text = "Создание статьи"
        viewModel.goBackEvent.subscribe(this, ::pop)
        viewModel.materialLiveData.subscribe(this, ::loadMaterial)
        viewModel.screenStateLiveData.subscribe(this, ::applyViewState)
        materialEditOk.setOnClickListener {
            viewModel.onSaveClicked(
                materialEditTitle.text.toString(),
                materialEditDescription.text.toString(),
                materialEditText.text.toString()
            )
        }
    }

    private fun applyViewState(state: ScreenState) {
        when (state) {
            is ScreenState.Default -> {
                progressBar.isVisible = false
                scrollView.isVisible = true
            }
            is ScreenState.Loading -> {
                progressBar.isVisible = true
                scrollView.isVisible = false
            }
        }
    }

    private fun loadMaterial(material: Material) {
        materialEditTitle.setText(material.title)
        materialEditDescription.setText(material.description)
        materialEditText.setText(material.text)
        viewModel.screenStateLiveData.value = ScreenState.Default
    }
}