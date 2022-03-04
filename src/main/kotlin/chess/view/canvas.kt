package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.game.Game
import chess.game.checkCoords
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import tornadofx.View

class CanvasHandler(val view: MainView) {

    fun scaleCanvas(canvas: Canvas, anchorPane: AnchorPane) {

        val desiredWidth = anchorPane.width - (anchorPane.width * 3.0/16.0 * 2)
        val desiredHeight = anchorPane.height - (anchorPane.height * 1.0/12.0 * 2)

        canvas.scaleX = desiredWidth / canvas.width
        canvas.scaleY = desiredHeight / canvas.height

        if (canvas.scaleX > canvas.scaleY) {
            canvas.scaleX = canvas.scaleY
        } else {
            canvas.scaleY = canvas.scaleX
        }

    }

    fun drawBoardBackground() {

        view.margin = view.boardCanvas.width * view.boardMargin
        view.sizeActual = view.boardCanvas.width - view.boardCanvas.width * view.boardMargin * 2
        view.squareSize = view.sizeActual / 8

        val gCon = view.boardCanvas.graphicsContext2D

        gCon.fill = Color.GREY
        gCon.fillRect(0.0, 0.0, view.boardCanvas.width, view.boardCanvas.height)
        //gCon.fill = WHITE
        //gCon.fillRect(margin, margin, boardBackground.width - 2 * margin, boardBackground.height - 2 * margin)

        for (x in 0..7) {
            for (y in 0..7) {
                if (gCon.fill == Color.WHITE)
                    gCon.fill = Color.GREY
                else gCon.fill = Color.WHITE
                gCon.fillRect((x * view.squareSize) + view.margin,
                        (y * view.squareSize) + view.margin,
                        view.squareSize,
                        view.squareSize)
            }
            if (gCon.fill == Color.WHITE)
                gCon.fill = Color.GREY
            else gCon.fill = Color.WHITE
        }
    }

    fun highlightSquare(canvas: Canvas, coords: IntArray, highlightWidth: Int = 3) {
        if (checkCoords(coords)){
            val gCon = canvas.graphicsContext2D
            val origin = canvas.width * view.boardMargin
            gCon.fill = Color.RED

            gCon.fillRect(origin + (coords[0] * view.squareSize) - highlightWidth,
                    origin + (coords[1] * view.squareSize) - highlightWidth,
                    view.squareSize + highlightWidth * 2,
                    view.squareSize + highlightWidth * 2)

            gCon.clearRect(origin + (coords[0] * view.squareSize), origin + (coords[1] * view.squareSize), view.squareSize, view.squareSize)
        }
    }

    fun fillSquare(canvas: Canvas, coords: IntArray, highlightColor: Color) {
        if (coords[0] >= 0 && coords[1] >= 0){
            val gCon = canvas.graphicsContext2D
            val origin = canvas.width * view.boardMargin
            gCon.fill = highlightColor

            gCon.fillRect(origin + (coords[0] * view.squareSize),
                    origin + (coords[1] * view.squareSize),
                    view.squareSize,
                    view.squareSize)
        }
    }

    fun fillMoves(moves: Array<out IntArray>?) {

        if (moves != null) {
            for (move in moves) {
                fillSquare(view.moveHighlightCanvas, move, Color.rgb(0, 255, 125, 0.5))
            }
        }
        //fillSquare(moveHightlightCanvas, coords, rgb(255, 255, 0, 0.5))

    }

    fun drawPieces(board: Array<Array<Piece?>>) {
        //TODO(later) OPTIMIZE MY GOD IS THIS SLOW (lesser priority now that the canvas is being scaled)

        val origin = view.pieceCanvas.width * view.boardMargin
        val gCon = view.pieceCanvas.graphicsContext2D

        gCon.clearRect(0.0, 0.0, view.pieceCanvas.width, view.pieceCanvas.height)

        var gBoard = Array(8) { Array<Piece?>(8) { null } }
        if (view.activeSide == PieceColor.WHITE) {
        // reverses the board and makes the active side be the one on the bottom
            for (i in 0..7) {
                for (j in 0..7) {
                    gBoard[j][7 - i] = board[j][i]
                }
            }
        } else {
            gBoard = board
        }

        for (i in 0..7) {
            for (j in 0..7) {
                if (gBoard[j][i] != null) {
                    val temp = gBoard[j][i]
                    if (temp != null) {
                        gCon.drawImage(
                                getPieceImage(temp.type, temp.color),
                                origin + (j * view.squareSize),
                                origin + (i * view.squareSize),
                                view.squareSize, view.squareSize)
                    }
                }
            }
        }
        view.pieceCanvas.toFront()
    }
}