package com.github.alxmag.loremipsumgenerator.template

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import net.datafaker.Faker

@Service
class FakerManager(private val project: Project) {

    // TODO implement her project settings aware logic in future
    fun getFaker() = Faker()

    companion object {
        fun getInstance(project: Project) = project.service<FakerManager>()
    }
}