package com.maxsch.symmetricguide.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxsch.symmetricguide.data.datasource.material.MaterialDao
import com.maxsch.symmetricguide.data.datasource.user.UserDao
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.user.User

@Database(entities = [User::class, Material::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun materialDao(): MaterialDao
}