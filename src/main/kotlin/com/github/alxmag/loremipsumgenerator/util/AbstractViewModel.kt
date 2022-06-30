package com.github.alxmag.loremipsumgenerator.util

import com.intellij.openapi.observable.properties.PropertyGraph

abstract class AbstractViewModel(private val graph: PropertyGraph = PropertyGraph()) {
    fun <T> property(initial: T) = graph.property(initial)
}