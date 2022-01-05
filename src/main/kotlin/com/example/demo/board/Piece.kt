package org.example.board

import org.example.board.Piece.pieceType
import org.example.board.Piece.pieceColor

class Piece(var type: pieceType, var color: pieceColor) {
    enum class pieceType {
        KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN
    }

    enum class pieceColor {
        WHITE, BLACK
    }

    override fun toString(): String {
        return "Piece{" +
                "type=" + type +
                ", color=" + color +
                '}'
    }
}