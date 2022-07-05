package com.github.alxmag.loremipsumgenerator.action.name

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.thedeanda.lorem.LoremIpsum

@Service
class LoremNameGenerator {

    private val lorem = LoremIpsum.getInstance()

    fun generateName(loremNameModel: LoremNameModel): String = when (loremNameModel.pattern) {
        NamePattern.FULL_NAME -> when (loremNameModel.gender) {
            Gender.MALE -> lorem.nameMale
            Gender.FEMALE -> lorem.nameFemale
            Gender.ANY -> lorem.name
        }

        NamePattern.FIRSTNAME -> when (loremNameModel.gender) {
            Gender.MALE -> lorem.firstNameMale
            Gender.FEMALE -> lorem.firstNameFemale
            Gender.ANY -> lorem.firstName
        }

        NamePattern.LASTNAME -> lorem.lastName
    }

    companion object {
        fun getInstance() = service<LoremNameGenerator>()
    }
}