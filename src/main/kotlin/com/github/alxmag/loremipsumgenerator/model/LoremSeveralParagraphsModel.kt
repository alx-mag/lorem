package com.github.alxmag.loremipsumgenerator.model

import com.github.alxmag.loremipsumgenerator.util.MinMax

class LoremSeveralParagraphsModel(
    var sentencesPerParagraph: MinMax = MinMax(5, 20)
) : LoremParagraphModel()