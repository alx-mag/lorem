package com.github.alxmag.loremipsumgenerator.action

import com.github.alxmag.loremipsumgenerator.generate.LoremPerformableActionGroupBase

class SentenceActionGroup : LoremPerformableActionGroupBase(RandomSentenceAction()) {
    companion object {
        const val ID = "lorem.SentenceActionGroup"
    }
}