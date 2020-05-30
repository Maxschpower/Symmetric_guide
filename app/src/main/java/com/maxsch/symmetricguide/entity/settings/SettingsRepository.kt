package com.maxsch.symmetricguide.entity.settings

interface SettingsRepository {

    fun updateFontSize(newFontSize: Int)

    fun getFontSize(): Int
}