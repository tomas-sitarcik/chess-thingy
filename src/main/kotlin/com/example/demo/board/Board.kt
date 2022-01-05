package org.example.board

import org.example.board.Piece
import java.lang.StringBuilder
import org.example.board.Piece.pieceColor

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
        var color = pieceColor.WHITE
        var i = 0
        while (i < 8) {
            for (j in 0..7) { //pawns
                if (i == 0) {
                    board[i + 1][j] = Piece(Piece.pieceType.PAWN, color)
                } else {
                    board[i - 1][j] = Piece(Piece.pieceType.PAWN, color)
                }
            }
            //rooks
            board[i][0] = Piece(Piece.pieceType.ROOK, color)
            board[i][7] = Piece(Piece.pieceType.ROOK, color)

            //knights
            board[i][1] = Piece(Piece.pieceType.KNIGHT, color)
            board[i][6] = Piece(Piece.pieceType.KNIGHT, color)

            //bishops
            board[i][2] = Piece(Piece.pieceType.BISHOP, color)
            board[i][5] = Piece(Piece.pieceType.BISHOP, color)

            //royalty
            board[i][3] = Piece(Piece.pieceType.QUEEN, color)
            board[i][4] = Piece(Piece.pieceType.KING, color)
            color = pieceColor.BLACK
            i += 7
        }
    }
}