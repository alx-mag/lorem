package com.github.alxmag.loremipsumgenerator.action.preview

import com.github.alxmag.loremipsumgenerator.template.FakerManager
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.Panel
import net.datafaker.Faker

abstract class SimpleFakerLoremTemplate(project: Project, override val title: String) : LoremTemplate {

    private val fakerManager = FakerManager.getInstance(project)

    final override fun generate(): String = doGenerate(fakerManager.getFaker())

    override fun renderAdditionalParams(panel: Panel) {
        // Do nothing for simple templates
    }

    protected abstract fun doGenerate(faker: Faker): String

    companion object {
        fun simpleLoremTemplate(
            project: Project,
            title: String,
            operation: (Faker) -> String
        ) = object : SimpleFakerLoremTemplate(project, title) {
            override fun doGenerate(faker: Faker): String = operation(faker)
        }
    }
}