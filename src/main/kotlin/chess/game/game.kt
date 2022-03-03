package chess.game

import chess.board.*
import chess.view.MainView
import chess.view.drawPieces
import chess.view.fillMoves
import javafx.scene.canvas.Canvas
import javafx.stage.Stage
import tornadofx.FX.Companion.find
import tornadofx.Scope
import tornadofx.UIComponent
import tornadofx.WorkspaceApp
import tornadofx.getChildList
import java.awt.event.MouseEvent
import kotlin.reflect.KClass

fun clickEvents(event: javafx.scene.input.MouseEvent, view: MainView) {
    val coords = determineBoardCoords(event.x, event.y, true, view)

    if (checkCoords(coords)){
        view.wipeCanvas(view.moveHighlightCanvas)

        /*
        print(event.x)
        print(" ")
        println(event.y)
         */

        if (view.activeSide == getPiece(view.activeSquare!!, view.mainBoard)?.color){
            movePiece(coords, view.activeSquare, view.validMoves, view.mainBoard, view)
        }


        if (!coords.contentEquals(view.activeSquare)) {
            view.setActiveSquare(coords)
        }


        if (view.activeSquare != null) {
            if (view.activeSide == getPiece(view.activeSquare!!, view.mainBoard)?.color){
                view.validMoves = filterPossibleMoves(view.activeSquare!!, view.mainBoard)
                fillMoves(view.validMoves, view)
                movePiece(coords, view.activeSquare, view.validMoves, view.mainBoard, view)
            }
            //view.switchSides()

        } else {
            view.setActiveSquare(coords)
            fillMoves(view.validMoves, view)
        }


        //wipeCanvas(moveHighlightCanvas)
    }
}

fun determineBoardCoords(rawX: Double, rawY: Double, realMode: Boolean = true, view: MainView): IntArray {

    var xActual: Double = rawX - view.pieceCanvas.width * view.boardMargin
    var yActual: Double = rawY - view.pieceCanvas.height * view.boardMargin

    var xCoord: Int = -1
    var yCoord: Int = -1

    if (xActual / view.squareSize > 8 || yActual / view.squareSize > 8 || xActual / view.squareSize < 0 || yActual / view.squareSize < 0) {
        xActual = -1.0
        yActual = -1.0
    } else {

        if (view.activeSide == PieceColor.WHITE && realMode) {
            xCoord =  (xActual / view.squareSize).toInt()
            yCoord =  7 - (yActual / view.squareSize).toInt()
        } else {
            xCoord = (xActual / view.squareSize).toInt()
            yCoord = (yActual / view.squareSize).toInt()
        }

    }

    return intArrayOf(xCoord, yCoord)
}

fun movePiece(location: IntArray?, destination: IntArray?, moves: Array<out IntArray>?,
              board: Array<Array<Piece?>>, view: MainView) {

    if (moves != null) {
        for (move in moves!!) {
            if (location.contentEquals(move)) {
                move(destination!!, move, board)
                view.unsetActiveSquare()
                view.switchSides()
            }
        }
    }
    drawPieces(board, view)
}

fun checkCoords(coords: IntArray): Boolean {
    return coords[0] in 0..7 && coords[1] in 0..7
}

fun filterPossibleMoves(coords: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

    var possibleMoves: Array<out IntArray>? = null

    if (checkCoords(coords)) {
        if (getPiece(coords, board) is Piece) {
            var color = getPiece(coords, board)?.color
            possibleMoves = getPossibleMoves(coords, board)
            if (possibleMoves != null) {
                for (move in possibleMoves) {
                    if (color == getPiece(move, board)?.color) {
                        move[0] = -1
                        move[1] = -1
                    }
                }
            }
        }
    }

    if (possibleMoves == null) {
        println("fuck")
    }

    return possibleMoves
}