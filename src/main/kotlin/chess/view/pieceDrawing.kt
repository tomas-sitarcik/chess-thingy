package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image

fun getPieceImage(type: PieceType, color: PieceColor): Image {
    //TODO add the actual piece images and their license (LOL)

    return when (color) {
        PieceColor.BLACK -> when (type) {
            PieceType.KING -> Image("file:src/resources/images/pieces/black_king.png")
            PieceType.QUEEN -> Image("file:src/resources/images/pieces/black_queen.png")
            PieceType.ROOK -> Image("file:src/resources/images/pieces/black_rook.png")
            PieceType.KNIGHT -> Image("file:src/resources/images/pieces/black_knight.png")
            PieceType.BISHOP -> Image("file:src/resources/images/pieces/black_bishop.png")
            PieceType.PAWN -> Image("file:src/resources/images/pieces/black_pawn.png")
        }

        PieceColor.WHITE -> when (type) {
            PieceType.KING -> Image("file:src/resources/images/pieces/white_king.png")
            PieceType.QUEEN -> Image("file:src/resources/images/pieces/white_queen.png")
            PieceType.ROOK -> Image("file:src/resources/images/pieces/white_rook.png")
            PieceType.KNIGHT -> Image("file:src/resources/images/pieces/white_knight.png")
            PieceType.BISHOP -> Image("file:src/resources/images/pieces/white_bishop.png")
            PieceType.PAWN -> Image("file:src/resources/images/pieces/white_pawn.png")
        }
    }
}

fun drawPieces(board: Array<Array<Piece?>>, canvas: Canvas, activeSide: PieceColor, boardMargin: Double, squareSize: Double) {
    //TODO(later) OPTIMIZE MY GOD IS THIS SLOW (lesser priority now that the canvas is being scaled)

    val origin = canvas.width * boardMargin
    val gCon = canvas.graphicsContext2D

    gCon.clearRect(0.0, 0.0, canvas.width, canvas.height)

    var gBoard = Array(8) { Array<Piece?>(8) { null } }
    if (activeSide == PieceColor.WHITE) { // reverses the board and makes the active side be the one on the bottom
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
                var temp = gBoard[j][i]
                if (temp != null) {
                    gCon.drawImage(getPieceImage(temp.type, temp.color), origin + (j * squareSize), origin + (i * squareSize), squareSize, squareSize)
                }
            }
        }
    }
    canvas.toFront()
}