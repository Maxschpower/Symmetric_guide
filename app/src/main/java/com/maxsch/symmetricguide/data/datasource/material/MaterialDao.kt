package com.maxsch.symmetricguide.data.datasource.material

import androidx.room.*
import com.maxsch.symmetricguide.entity.material.Material
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MaterialDao {

    @Insert
    fun addMaterial(material: Material): Single<Unit>

    @Insert
    fun addMaterials(material: List<Material>): Single<Unit>

    @Delete
    fun deleteMaterial(material: Material): Single<Unit>

    @Update
    fun updateMaterial(material: Material): Single<Unit>

    @Query("SELECT * FROM materials WHERE id = :id LIMIT 1")
    fun getMaterialById(id: Int): Single<Material>

    @Query("SELECT * FROM materials WHERE id = :id LIMIT 1")
    fun observeMaterial(id: Int): Flowable<Material>

    @Query("SELECT * FROM materials")
    fun observeMaterials(): Flowable<List<Material>>
}