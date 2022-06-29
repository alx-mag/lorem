package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.util.MinMax
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persists [com.github.alxmag.loremipsumgenerator.model.LoremModel] instances for dialogs
 */
@State(name = "LoremUnits", storages = [Storage("lorem-generator.xml")])
@Service
class LoremModelStateService : PersistentStateComponent<LoremModelStateService> {

    var wordsPerSentence = MinMax(5, 10)
    var sentencesPerParagraph = MinMax(5, 10)

    override fun getState() = this
    override fun loadState(state: LoremModelStateService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance() = service<LoremModelStateService>()
    }
}