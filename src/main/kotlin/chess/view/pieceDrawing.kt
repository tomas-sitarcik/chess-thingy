package chess.view

import chess.board.PieceColor
import chess.board.PieceType
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