package com.maxsch.symmetricguide.ui.material

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.maxsch.symmetricguide.FontSizeController
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.presentation.materials.MaterialsViewModel
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import com.maxsch.symmetricguide.ui.material.editor.MaterialEditorFragment
import kotlinx.android.synthetic.main.fragment_material.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MaterialFragment : BaseFragment(R.layout.fragment_material) {

    companion object {
        const val BUNDLE_MATERIAL_ID = "mat_id"
    }

    private val viewModel: MaterialsViewModel by viewModel {
        parametersOf(requireArguments().getInt(BUNDLE_MATERIAL_ID, 0))
    }

    private val fontSizeController: FontSizeController by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.materialLiveData.subscribe(this, ::loadData)

        webview.apply {
            settings.apply {
                defaultFontSize = fontSizeController.getFontSize()
                javaScriptEnabled = true
                builtInZoomControls = true
                displayZoomControls = false
                loadWithOverviewMode = true
                useWideViewPort = true
            }
            webViewClient = LoadingWebView()
        }
        edit.setOnClickListener {
            findNavController().navigate(
                R.id.action_materialFragment_to_materialEditor,
                Bundle().apply {
                    putInt(
                        MaterialEditorFragment.BUNDLE_MATERIAL_ID,
                        arguments?.getInt(BUNDLE_MATERIAL_ID) ?: 0
                    )
                })
        }
    }

    private fun loadData(material: Material) {
        webview.loadData(material.text, "text/html", "UTF-8")
        title.text = material.title
    }

    inner class LoadingWebView : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.isVisible = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.isVisible = false
        }
    }
}