package chess.board

import java.lang.StringBuilder

class Board {

    var board: Array<Array<Piece?>> = Array(8) { arrayOf<Piece?>() }
    override fun toString(): String {
        val consoleBoard = StringBuilder()
        for (row in board) {
            for (piece in row) {
                if (piece != null) {
                    consoleBoard.append("[").append(piece.type.name, 0, 2)
                    consoleBoard.append(piece.color.name, 0, 2).append("] ")
                } else {
                    consoleBoard.append("[----] ")
                }
            }
            consoleBoard.append("\n")
        }
        return consoleBoard.toString()
    }

    init {
        var color = PieceColor.WHITE
        var i = 0
        while (i < 8) {
            for (j in 0..7) { //pawns
                if (i == 0) {
                    board[i + 1][j] = Piece(PieceType.PAWN, color)
                } else {
                    board[i - 1][j] = Piece(PieceType.PAWN, color)
                }
            }
            //rooks
            board[i][0] = Piece(PieceType.ROOK, color)
            board[i][7] = Piece(PieceType.ROOK, color)

            //knights
            board[i][1] = Piece(PieceType.KNIGHT, color)
            board[i][6] = Piece(PieceType.KNIGHT, color)

            //bishops
            board[i][2] = Piece(PieceType.BISHOP, color)
            board[i][5] = Piece(PieceType.BISHOP, color)

            //royalty
            board[i][3] = Piece(PieceType.QUEEN, color)
            board[i][4] = Piece(PieceType.KING, color)
            color = PieceColor.BLACK
            i += 7
        }
    }
}