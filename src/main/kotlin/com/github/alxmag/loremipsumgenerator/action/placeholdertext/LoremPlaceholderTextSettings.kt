package com.github.alxmag.loremipsumgenerator.action.placeholdertext

import com.github.alxmag.loremipsumgenerator.util.LoremConstants.STORAGE_PATH
import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.github.alxmag.loremipsumgenerator.util.removeDuplicates
import com.intellij.openapi.components.*

@State(name = "LoremUnits", storages = [Storage(STORAGE_PATH)])
@Service
class LoremPlaceholderTextSettings : SimplePersistentStateComponent<LoremPlaceholderTextSettings.State>(State()) {

    fun saveLastTextModel(lastTextModel: LoremPlaceholderTextModel) {
        state.preselectedLoremPlaceholderTextModel = lastTextModel

        // Put last selected model on top of history list
        state.textModelsHistory = buildList {
            add(lastTextModel)
            addAll(state.textModelsHistory)
        }.removeDuplicates().toMutableList()
    }

    val preselectedLoremPlaceholderTextModel get() = state.preselectedLoremPlaceholderTextModel

    var wordsPerSentence
        set(value) {
            state.wordsPerSentence = value
        }
        get() = state.wordsPerSentence.copy()

    val textModelsHistory get() = state.textModelsHistory

    class State : BaseState() {
        var preselectedLoremPlaceholderTextModel: LoremPlaceholderTextModel by property(LoremPlaceholderTextModel()) {
            it == LoremPlaceholderTextModel()
        }

        var textModelsHistory by list<LoremPlaceholderTextModel>()

        var wordsPerSentence by property(
            defaultWordsPerSentence,
            defaultWordsPerSentence::equals
        )

        companion object {
            val defaultWordsPerSentence: MinMax = MinMax(5, 10)
        }
    }

    companion object {
        fun getInstance() = service<LoremPlaceholderTextSettings>()
    }
}