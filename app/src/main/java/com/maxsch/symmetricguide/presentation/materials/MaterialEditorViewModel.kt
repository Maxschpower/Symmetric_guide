package com.maxsch.symmetricguide.presentation.materials

import androidx.lifecycle.MutableLiveData
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.material.repository.MaterialRepository
import com.maxsch.symmetricguide.presentation.BaseViewModel
import com.maxsch.symmetricguide.presentation.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MaterialEditorViewModel(
    private val materialRepository: MaterialRepository,
    materialId: Int
) : BaseViewModel() {

    private var editedMaterial: Material? = null
    val goBackEvent = SingleLiveEvent<Unit>()
    val materialLiveData = MutableLiveData<Material>()
    val screenStateLiveData = MutableLiveData<ScreenState>(ScreenState.Loading)

    init {
        if (materialId != 0) {
            materialRepository.getMaterialById(materialId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    screenStateLiveData.value = ScreenState.Loading
                    editedMaterial = it
                    materialLiveData.value = it
                }, {
                    screenStateLiveData.value = ScreenState.Default
                })
                .untilDestroy()
        } else {
            screenStateLiveData.value = ScreenState.Default
        }
    }

    fun onSaveClicked(title: String, description: String, text: String) {
        screenStateLiveData.value = ScreenState.Default

        if (editedMaterial != null) {
            editedMaterial?.copy(title = title, text = text)?.let {
                materialRepository.updateMaterial(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        goBackEvent()
                    }, {
                        goBackEvent()
                    })
                    .untilDestroy()
            }
        } else {
            materialRepository.addMaterial(Material(0, title, text, description))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    goBackEvent()
                }, {
                    goBackEvent()
                })
                .untilDestroy()
        }
    }
}

sealed class ScreenState {
    object Loading : ScreenState()

    object Default : ScreenState()
}