package com.github.alxmag.loremipsumgenerator.action.address

import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorAction
import com.github.alxmag.loremipsumgenerator.action.base.LoremTextFactoryActonHandler.Companion.simpleFakerHandler

class LoremCityAction : LoremEditorAction(simpleFakerHandler { it.address().city() })
class LoremCountryAction : LoremEditorAction(simpleFakerHandler { it.address().country() })
class LoremStateAction : LoremEditorAction(simpleFakerHandler { it.address().state() })

// TODO register
class LoremStreetAction : LoremEditorAction(
    simpleFakerHandler { it.address().streetAddress() },
    message("action.street.address")
)