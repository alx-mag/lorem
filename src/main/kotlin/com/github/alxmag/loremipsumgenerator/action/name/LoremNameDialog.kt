package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.util.ListCellRendererFactory.simpleRenderer
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.layout.selectedValueMatches
import javax.swing.JComponent

class LoremNameDialog(initialModel: LoremNameModel, project: Project) : DialogWrapper(project) {

    private val graph = PropertyGraph()

    private val namePattern = graph.property(initialModel.pattern)
    private val gender = graph.property(initialModel.gender)
    private val hint = graph.property(createHint())
        .apply {
            dependsOn(namePattern, ::createHint)
            dependsOn(gender, ::createHint)
        }

    init {
        title = "Generate Name"
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row {
            val patternCombo = comboBox(NamePattern.values().toList(), simpleRenderer(NamePattern::visibleName))
                .bindItem(namePattern)
                .focused()
                .component

            comboBox(Gender.values().toList(), simpleRenderer(Gender::visibleName))
                .bindItem(gender)
                .enabledIf(patternCombo.selectedValueMatches {
                    it != NamePattern.LASTNAME
                })
        }

        separator()

        row {
            comment("").bindText(hint)
        }
    }

    private fun createHint(): String = "Example result: " + createNameForHint(namePattern.get(), gender.get())

    private fun createNameForHint(namePattern: NamePattern, gender: Gender): String {
        return when (namePattern) {
            NamePattern.FIRSTNAME -> when (gender) {
                Gender.MALE -> "Homer"
                Gender.FEMALE -> "Marge"
            }

            NamePattern.LASTNAME -> "Simpson"
            NamePattern.FULL_NAME -> createNameForHint(NamePattern.FIRSTNAME, gender)
                .plus(" ")
                .plus(createNameForHint(NamePattern.LASTNAME, gender))
        }
    }

    fun getModel() = LoremNameModel(
        namePattern.get(),
        gender.get()
    )
}