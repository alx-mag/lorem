package com.github.alxmag.loremipsumgenerator.util

import com.intellij.openapi.ui.DialogWrapper

fun <T : DialogWrapper> T.takeIfOk() = takeIf { showAndGet() }