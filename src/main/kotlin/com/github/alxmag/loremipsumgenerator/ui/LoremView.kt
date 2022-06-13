package com.github.alxmag.loremipsumgenerator.ui

import com.github.alxmag.loremipsumgenerator.model.LoremModel
import javax.swing.JComponent

abstract class LoremView<T : LoremModel>(protected val initialModel: T) {

    abstract fun createComponent(): JComponent

    abstract fun createModel(): T
}

