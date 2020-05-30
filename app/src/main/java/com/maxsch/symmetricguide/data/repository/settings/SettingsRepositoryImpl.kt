package com.maxsch.symmetricguide.data.repository.settings

import com.maxsch.symmetricguide.data.datasource.settings.SettingsDataSource
import com.maxsch.symmetricguide.entity.settings.SettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {

    override fun updateFontSize(newFontSize: Int) {
        settingsDataSource.fontSize = newFontSize
    }

    override fun getFontSize(): Int = settingsDataSource.fontSize
}