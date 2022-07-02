package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.action.placeholdertext.LoremPlaceholderTextModel
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persists [com.github.alxmag.loremipsumgenerator.model.LoremModel] instances for dialogs
 */
@State(name = "LoremUnits", storages = [Storage("lorem-generator.xml")])
@Service
class LoremHistoryService : PersistentStateComponent<LoremHistoryService>, BaseState() {

    private val loremConstants = LoremConstants.instance

    var preselectedLoremPlaceholderTextModel: LoremPlaceholderTextModel by property(LoremPlaceholderTextModel()) {
        it == LoremPlaceholderTextModel()
    }

    var textModelsHistory by list<LoremPlaceholderTextModel>()

    var wordsPerSentence by property(
        loremConstants.defaultWordsPerSentence,
        loremConstants.defaultWordsPerSentence::equals
    )

    var sentencesPerParagraph = MinMax(5, 10)

    fun saveLastTextModel(lastTextModel: LoremPlaceholderTextModel) {
        preselectedLoremPlaceholderTextModel = lastTextModel
        textModelsHistory = buildList {
            add(lastTextModel)
            addAll(textModelsHistory)
        }.removeDuplicates().toMutableList()
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