package com.maxsch.symmetricguide.ui.material.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.maxsch.symmetricguide.R
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.presentation.materials.list.MaterialsListViewModel
import com.maxsch.symmetricguide.presentation.subscribe
import com.maxsch.symmetricguide.ui.BaseFragment
import com.maxsch.symmetricguide.ui.material.MaterialFragment
import kotlinx.android.synthetic.main.fragment_materials_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialsListFragment : BaseFragment(R.layout.fragment_materials_list) {

    private val viewModel: MaterialsListViewModel by viewModel()

    private val adapter = MaterialsAdapter(::openMaterial, ::showDeleteDialog)

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        viewModel.materialsLiveData.subscribe(this, ::showMaterials)

        materialsList.adapter = adapter
        materialsAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_MaterialsFragment_to_materialEditor)
        }
    }

    private fun showMaterials(materials: List<Material>) {
        adapter.items = materials.toMutableList()
    }

    private fun openSettingsScreen() {
        findNavController().navigate(R.id.action_MaterialsFragment_to_SettingsFragment)
    }

    private fun openMaterial(material: Int) {
        findNavController().navigate(
            R.id.action_MaterialsFragment_to_materialFragment,
            Bundle().apply { putInt(MaterialFragment.BUNDLE_MATERIAL_ID, material) }
        )
    }

    private fun showDeleteDialog(material: Material) {
        AlertDialog.Builder(requireContext())
            .setTitle("Вы точно хотите удалить статью?")
            .setMessage("Вы собираетесь удалить статью")
            .setPositiveButton("Да") { _, _ ->
                viewModel.deleteMaterial(material)
            }
            .setNegativeButton("Нет") { _, _ -> }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> openSettingsScreen()
        }
        return super.onOptionsItemSelected(item)
    }
}
