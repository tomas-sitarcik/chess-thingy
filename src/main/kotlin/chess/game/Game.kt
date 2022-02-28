package chess.game

import chess.board.Piece
import chess.board.initBoard
import chess.view.MainView
import tornadofx.FX.Companion.find
import tornadofx.UIComponent
import tornadofx.WorkspaceApp
import kotlin.reflect.KClass

class Game(initiallyDockedView: KClass<out UIComponent>, val board: Array<Array<Piece?>>, var multiplayer: Boolean = true) : WorkspaceApp(initiallyDockedView) { // leaving multiplayer on by default, the AI is the last thing im gonna do



    fun start() {
        board = initBoard()
        var mainView: MainView by find<>()
    }
}