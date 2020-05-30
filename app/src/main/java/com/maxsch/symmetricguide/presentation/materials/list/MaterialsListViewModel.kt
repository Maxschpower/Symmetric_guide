package com.maxsch.symmetricguide.presentation.materials.list

import androidx.lifecycle.MutableLiveData
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.material.repository.MaterialRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MaterialsListViewModel(
    private val materialRepository: MaterialRepository
) : BaseViewModel() {

    val materialsLiveData = MutableLiveData<List<Material>>()

    init {
        materialRepository.observeMaterials()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                materialsLiveData.value = it
            }
            .untilDestroy()
    }

    fun deleteMaterial(material: Material) {
        materialRepository.deleteMaterial(material)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .untilDestroy()
    }
}