package com.maxsch.symmetricguide.data.repository.material

import com.maxsch.symmetricguide.data.datasource.material.MaterialDao
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.material.repository.MaterialRepository
import io.reactivex.Flowable
import io.reactivex.Single

class MaterialRepositoryImpl(
    private val dao: MaterialDao
) : MaterialRepository {
    override fun observeMaterials(): Flowable<List<Material>> = dao.observeMaterials()

    override fun getMaterialById(id: Int): Single<Material> = dao.getMaterialById(id)

    override fun observeMaterial(id: Int): Flowable<Material> = dao.observeMaterial(id)

    override fun updateMaterial(updatedMaterial: Material): Single<Unit> =
        dao.updateMaterial(updatedMaterial)

    override fun deleteMaterial(material: Material): Single<Unit> = dao.deleteMaterial(material)

    override fun addMaterial(material: Material): Single<Unit> = dao.addMaterial(material)
}