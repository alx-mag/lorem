package com.github.alxmag.loremipsumgenerator.services

import com.github.alxmag.loremipsumgenerator.model.LoremParagraphModel
import com.github.alxmag.loremipsumgenerator.model.LoremSentenceModel
import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Persists [com.github.alxmag.loremipsumgenerator.model.LoremModel] instances for dialogs
 */
@State(name = "LoremUnits", storages = [Storage("lorem-generator.xml")])
@Service
class LoremModelStateService : PersistentStateComponent<LoremModelStateService> {

    var paragraph = LoremParagraphModel()
    var sentence = LoremSentenceModel()

    override fun getState() = this
    override fun loadState(state: LoremModelStateService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance() = service<LoremModelStateService>()
    }
}