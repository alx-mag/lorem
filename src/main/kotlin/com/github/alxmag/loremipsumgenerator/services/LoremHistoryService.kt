package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.model.LoremTextModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persists [com.github.alxmag.loremipsumgenerator.model.LoremModel] instances for dialogs
 */
@State(name = "LoremUnits", storages = [Storage("lorem-generator.xml")])
@Service
class LoremHistoryService : PersistentStateComponent<LoremHistoryService> {

    var preselectedLoremTextModel: LoremTextModel = LoremTextModel()

    var textModelsHistory: List<LoremTextModel> = listOf()

    var wordsPerSentence = MinMax(5, 10)
    var sentencesPerParagraph = MinMax(5, 10)

    fun saveLastTextModel(lastTextModel: LoremTextModel) {
        preselectedLoremTextModel = lastTextModel
        textModelsHistory = (listOf(lastTextModel) + textModelsHistory).removeDuplicates()
    }

    override fun getState() = this
    override fun loadState(state: LoremHistoryService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance() = service<LoremHistoryService>()
    }
}

private fun <T> List<T>.removeDuplicates(): List<T> {
    val seen = mutableSetOf<T>()
    return fold(mutableListOf()) { result, item ->
        if (!seen.contains(item)) {
            result.add(item)
            seen.add(item)
        }
        result
    }
}