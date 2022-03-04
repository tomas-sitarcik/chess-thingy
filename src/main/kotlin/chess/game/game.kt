package chess.game

import chess.board.*
import chess.view.CanvasHandler
import chess.view.MainView

fun checkCoords(coords: IntArray): Boolean {
    return coords[0] in 0..7 && coords[1] in 0..7
}

class Game(private val view: MainView, val canv: CanvasHandler) {

    fun determineBoardCoords(rawX: Double, rawY: Double, realMode: Boolean = true): IntArray {

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

    private fun movePiece(location: IntArray?, destination: IntArray?, moves: Array<out IntArray>?,
                          board: Array<Array<Piece?>>) {

        if (moves != null) {
            for (move in moves!!) {
                if (location.contentEquals(move)) {
                    move(destination!!, move, board)
                    view.unsetActiveSquare()
                    view.switchSides()
                }
            }
        }
        canv.drawPieces(board)
    }

    private fun filterPossibleMoves(coords: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

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

    fun clickEvents(event: javafx.scene.input.MouseEvent) {
        val coords = determineBoardCoords(event.x, event.y, true)

        if (checkCoords(coords)){
            view.wipeCanvas(view.moveHighlightCanvas)

            /*
            print(event.x)
            print(" ")
            println(event.y)
             */

            if (!coords.contentEquals(view.activeSquare)) {
                view.setActiveSquare(coords)
            }

            if (view.activeSide == getPiece(view.activeSquare!!, view.mainBoard)?.color
                && view.activeSquare != null){
                movePiece(coords, view.activeSquare, view.validMoves, view.mainBoard)
            }

            if (view.activeSquare != null) {
                if (view.activeSide == getPiece(view.activeSquare!!, view.mainBoard)?.color){
                    view.validMoves = filterPossibleMoves(view.activeSquare!!, view.mainBoard)
                    canv.fillMoves(view.validMoves)
                    movePiece(coords, view.activeSquare, view.validMoves, view.mainBoard)
                }
                //view.switchSides()

            } else {
                view.setActiveSquare(coords)
                canv.fillMoves(view.validMoves)
            }


            //wipeCanvas(moveHighlightCanvas)
        }
    }

}
