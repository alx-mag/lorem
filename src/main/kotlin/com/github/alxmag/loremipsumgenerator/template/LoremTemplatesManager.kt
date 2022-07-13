package com.github.alxmag.loremipsumgenerator.template

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.preview.SimpleFakerLoremTemplate.Companion.simpleLoremTemplate
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import net.datafaker.Faker

@Service
class LoremTemplatesManager(private val project: Project) {

    private val simpleNameTemplatesMap = mapOf<String, (Faker) -> String>(
        message("full.name") to { it.name().fullName() },
        message("firstname") to { it.name().firstName() },
        message("lastname") to { it.name().lastName() },

        message("setting.character", "Game of Thrones") to { it.gameOfThrones().character() },
        message("setting.character", "The Witcher") to { it.witcher().character() },
        message("setting.character", "Harry Potter") to { it.harryPotter().character() },

        message("artist") to { it.artist().name() },
        message("basketball.player") to { it.basketball().players() }
    )

    fun getNameTemplates() = simpleNameTemplatesMap.map { (title, operation) ->
        simpleLoremTemplate(project, title, operation)
    }

    companion object {
        fun getInstance(project: Project) = project.service<LoremTemplatesManager>()
    }
}