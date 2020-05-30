package com.maxsch.symmetricguide

import com.maxsch.symmetricguide.entity.settings.SettingsRepository

class FontSizeController(
    private val settingsRepository: SettingsRepository
) {

    fun getFontSize() = settingsRepository.getFontSize()

    fun changeFontSize(fontSize: Int) = settingsRepository.updateFontSize(fontSize)
}