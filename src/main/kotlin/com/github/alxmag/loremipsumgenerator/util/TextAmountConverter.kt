package com.github.alxmag.loremipsumgenerator.util

class TextAmountConverter(private val data: ConverterData) {

    fun convert(fromAmount: MinMax, fromUnit: TextAmountUnit, toUnit: TextAmountUnit): MinMax {
        if (fromUnit == toUnit) {
            return fromAmount
        }

        val nextUnit: TextAmountUnit
        val nextAmount: MinMax

        if (fromUnit > toUnit) {
            // Go to next child and get its amount
            nextUnit = fromUnit.requireChildUnit()
            nextAmount = fromAmount * getChildrenAmount(fromUnit, nextUnit)
        } else {
            // Go to next parent and get its amount
            nextUnit = fromUnit.requireParentUnit()
            nextAmount = fromAmount / getChildrenAmount(nextUnit, fromUnit)
        }

        return convert(nextAmount, nextUnit, toUnit)
    }

    private fun getChildrenAmount(parent: TextAmountUnit, child: TextAmountUnit): MinMax {
        return data.getChildrenAmount(parent, child) ?: throw IllegalStateException()
    }
}

interface ConverterData {
    fun getChildrenAmount(parent: TextAmountUnit, child: TextAmountUnit): MinMax?

    companion object {
        fun create(vararg containingRules: Triple<TextAmountUnit, TextAmountUnit, MinMax>) = create(containingRules.toList())

        fun create(containingRules: Collection<Triple<TextAmountUnit, TextAmountUnit, MinMax>>): ConverterData {
            return object : ConverterData {
                private val rulesMap = containingRules.toSet()
                    .groupBy { it.first }
                    .mapValues { (_, v) ->
                        v.associate { it.second to it.third }
                    }

                override fun getChildrenAmount(parent: TextAmountUnit, child: TextAmountUnit): MinMax? {
                    return rulesMap[parent]?.get(child)
                }
            }
        }
    }
}