package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.action.base.LoremEditorAction
import com.github.alxmag.loremipsumgenerator.action.base.LoremFakerActonHandler.Companion.simpleFakerHandler

class FullNameAction : LoremEditorAction(simpleFakerHandler { it.name().fullName() })
class FirstNameAction : LoremEditorAction(simpleFakerHandler { it.name().firstName() })
class LastNameAction : LoremEditorAction(simpleFakerHandler { it.name().lastName() })

class TheWitcherNameAction : LoremEditorAction(simpleFakerHandler { it.witcher().character() })
class RickAndMortyNameAction : LoremEditorAction(simpleFakerHandler { it.rickAndMorty().character() })
class GoTNameAction : LoremEditorAction(simpleFakerHandler { it.gameOfThrones().character() })
class BackToTheFutureNameAction : LoremEditorAction(simpleFakerHandler { it.backToTheFuture().character() })

class ArtistNameAction : LoremEditorAction(simpleFakerHandler { it.artist().name() })
class BasketballPlayerNameAction : LoremEditorAction(simpleFakerHandler { it.basketball().players() })