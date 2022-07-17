package com.github.alxmag.loremipsumgenerator.action.name

import com.github.alxmag.loremipsumgenerator.action.base.LoremFakerActonHandler.Companion.simpleFakerHandler
import com.intellij.openapi.editor.actionSystem.EditorAction

class FullNameAction : EditorAction(simpleFakerHandler { it.name().fullName() })
class FirstNameAction : EditorAction(simpleFakerHandler { it.name().firstName() })
class LastNameAction : EditorAction(simpleFakerHandler { it.name().lastName() })

class TheWitcherNameAction : EditorAction(simpleFakerHandler { it.witcher().character() })
class RickAndMortyNameAction : EditorAction(simpleFakerHandler { it.rickAndMorty().character() })
class GoTNameAction : EditorAction(simpleFakerHandler { it.gameOfThrones().character() })
class BackToTheFutureNameAction : EditorAction(simpleFakerHandler { it.backToTheFuture().character() })

class ArtistNameAction : EditorAction(simpleFakerHandler { it.artist().name() })
class BasketballPlayerNameAction : EditorAction(simpleFakerHandler { it.basketball().players() })