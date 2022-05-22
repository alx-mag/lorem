package com.github.alxmag.loremipsumgenerator.services

import com.intellij.openapi.project.Project
import com.github.alxmag.loremipsumgenerator.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
