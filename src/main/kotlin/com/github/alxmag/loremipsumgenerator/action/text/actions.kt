package com.github.alxmag.loremipsumgenerator.action.text

import com.github.alxmag.loremipsumgenerator.BUNDLE
import com.github.alxmag.loremipsumgenerator.MyBundle.message
import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorAction
import com.github.alxmag.loremipsumgenerator.action.base.LoremTextFactoryActonHandler.Companion.simpleFakerHandler
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.PropertyKey

class YodaQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.yoda().quote() }, quoteActionTitle("yoda"))

class HarryPotterQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.harryPotter().quote() }, quoteActionTitle("harry.potter"))

class WitcherQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.witcher().quote() }, quoteActionTitle("the.witcher"))

class GoTQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.gameOfThrones().quote() }, quoteActionTitle("game.of.thrones"))

class BackToTheFutureQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.backToTheFuture().quote() }, quoteActionTitle("back.to.the.future"))

class RickAndMortyQuoteAction :
    LoremEditorAction(simpleFakerHandler { it.rickAndMorty().quote() }, quoteActionTitle("rick.and.morty"))

private fun quoteActionTitle(@PropertyKey(resourceBundle = BUNDLE) base: String): @Nls String {
    return message("quote.title", message(base))
}