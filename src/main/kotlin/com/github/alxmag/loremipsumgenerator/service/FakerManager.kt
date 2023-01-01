package com.github.alxmag.loremipsumgenerator.service

import com.github.alxmag.loremipsumgenerator.settings.LoremPluginSettingsManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import net.datafaker.Faker
import java.util.*

@Service(Service.Level.PROJECT)
class FakerManager {

    fun getFaker(): Faker {
        val locale = LoremPluginSettingsManager.getInstance().state.locale
        return Faker(locale)
    }

    companion object {
        fun getInstance(project: Project) = project.service<FakerManager>()
    }
}