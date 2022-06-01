package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.intellij.ide.util.propComponentProperty
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.bindIntValue
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.castSafelyTo
import com.intellij.util.ui.JBUI.Panels.simplePanel
import javax.swing.JComponent

class SentenceWithNumberOfWordsDialog(project: Project) : DialogWrapper(project) {

    var words: Int by propComponentProperty(project, 5)

    private val propertyGraph = PropertyGraph()

    private val myText = propertyGraph.property("")
    private val myWords = propertyGraph.property(5).apply {
        afterChange {
            myText.set(it.toString())
        }
    }

    private val myPanel = simplePanel()
        .addToTop(panel {
            row(message("label.words")) {
                spinner((1..1000), 1)
                    .bindIntValue(getter = myWords::get, setter = myWords::set)
                    .component.addChangeListener {
                        it.source.castSafelyTo<JBIntSpinner>()
                            ?.number?.toString()
                            ?.let(myText::set)
                    }
            }
            separator()
        })
        .addToCenter(ScrollPaneFactory.createScrollPane(JBTextArea()))

    init {
        title = message("sentence.by.words.dialog.title")
        init()
    }

    override fun createCenterPanel(): JComponent = myPanel

    companion object {
        fun showAndGet(project: Project) = SentenceWithNumberOfWordsDialog(project)
            .takeIf { it.showAndGet() }
            ?.words
    }
}