package chess.game

import chess.board.Piece
import chess.board.initBoard
import chess.board.move
import chess.view.MainView
import javafx.scene.canvas.Canvas
import javafx.stage.Stage
import tornadofx.FX.Companion.find
import tornadofx.Scope
import tornadofx.UIComponent
import tornadofx.WorkspaceApp
import tornadofx.getChildList
import kotlin.reflect.KClass

fun movePiece(location: IntArray?, destination: IntArray?, moves: Array<out IntArray>?,
              board: Array<Array<Piece?>>) {

    if (moves != null) {
        for (move in moves!!) {
            if (location.contentEquals(move)) {
                move(destination!!, move, board)
            }
        }
    }
}