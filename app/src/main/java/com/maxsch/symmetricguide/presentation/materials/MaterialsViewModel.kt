package com.maxsch.symmetricguide.presentation.materials

import androidx.lifecycle.MutableLiveData
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.material.repository.MaterialRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MaterialsViewModel(
    materialRepository: MaterialRepository,
    id: Int
) : BaseViewModel() {

    val materialLiveData = MutableLiveData<Material>()

    init {
        materialRepository.observeMaterial(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                materialLiveData.value = it
            }, {

            })
            .untilDestroy()
    }
}