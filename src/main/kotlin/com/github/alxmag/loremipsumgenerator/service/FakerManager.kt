package com.github.alxmag.loremipsumgenerator.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import net.datafaker.Faker

@Service(Service.Level.PROJECT)
class FakerManager {

    //TODO implement per-project settings aware logic
    fun getFaker() = Faker()

    companion object {
        fun getInstance(project: Project) = project.service<FakerManager>()
    }
}