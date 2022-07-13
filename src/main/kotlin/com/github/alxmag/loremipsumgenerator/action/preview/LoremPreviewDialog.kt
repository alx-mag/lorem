package com.github.alxmag.loremipsumgenerator.action.preview

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.template.FakerManager
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.observable.properties.GraphProperty
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import net.datafaker.Faker
import javax.swing.JComponent

class LoremPreviewDialog(
    project: Project?,
    title: String,
    private val templates: List<LoremTemplate>,
    selectedIndex: Int = 0
) : DialogWrapper(project) {

    val vm = ViewModelImpl(templates[selectedIndex])

    init {
        this.title = title
        init()
    }

    override fun createCenterPanel(): JComponent = panel {
        row {
            comboBox(
                templates,
                SimpleListCellRenderer.create { label, value, _ ->
                    label.text = value.title
                }
            )
                .bindItem(vm.template)
                .focused()
                .applyToComponent {
                    // Set false to enable search
                    isSwingPopup = false
                }

            button(
                message("generate"),
                object : AnAction(AllIcons.Actions.Refresh) {
                    init {
                        registerCustomShortcutSet(refreshShortcut, rootPane)
                    }

                    override fun actionPerformed(e: AnActionEvent) = vm.updatePreviewText()
                }
            ).applyToComponent {
                // TODO get shortcut text from object
                toolTipText = "Ctrl + F5"
            }
        }
        renderAdditionalParamsUi(this)
        row {
            textArea()
                .horizontalAlign(HorizontalAlign.FILL)
                .verticalAlign(VerticalAlign.FILL)
                .resizableColumn()
                .bindText(vm.preview)
                // TODO get shortcut text from object
                .comment("Ctrl + F5 to generate new text.")
                .applyToComponent {
                    lineWrap = true
                    rows = 3
                    isEditable = false
                }
        }
            .resizableRow()
            .layout(RowLayout.INDEPENDENT)
    }

    private fun renderAdditionalParamsUi(panel: Panel) {

    }

    fun getText() = vm.preview.get()

    interface ViewModel {
        val graph: PropertyGraph
        val template: GraphProperty<LoremTemplate>
        val preview: GraphProperty<String>
    }

    inner class ViewModelImpl(initialTemplate: LoremTemplate) : ViewModel {
        override val graph: PropertyGraph = PropertyGraph()
        override val template = graph.property(initialTemplate)
        override val preview = graph.property(initialTemplate.generate())

        init {
            template.afterPropagation(::updatePreviewText)
        }

        fun updatePreviewText() {
            val newText = template.get().generate()
            preview.set(newText)
        }
    }

    companion object {
        val refreshShortcut
            get() = ActionManager.getInstance()
                .getAction(IdeActions.ACTION_REFRESH)
                .shortcutSet
    }
}

interface LoremTemplate {
    val title: String

    fun generate(): String

    fun renderAdditionalParams(panel: Panel)
}

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