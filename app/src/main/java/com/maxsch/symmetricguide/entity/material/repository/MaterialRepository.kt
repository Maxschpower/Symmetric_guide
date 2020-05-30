package com.maxsch.symmetricguide.entity.material.repository

import com.maxsch.symmetricguide.entity.material.Material
import io.reactivex.Flowable
import io.reactivex.Single

interface MaterialRepository {

    fun observeMaterials(): Flowable<List<Material>>

    fun getMaterialById(id: Int): Single<Material>

    fun observeMaterial(id: Int): Flowable<Material>

    fun updateMaterial(updatedMaterial: Material): Single<Unit>

    fun deleteMaterial(material: Material): Single<Unit>

    fun addMaterial(material: Material): Single<Unit>
}