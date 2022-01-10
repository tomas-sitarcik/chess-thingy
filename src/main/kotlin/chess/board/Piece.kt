package chess.board

class Piece(var type: PieceType, var color: PieceColor) {
    override fun toString(): String {
        return "Piece{" +
                "type=" + type +
                ", color=" + color +
                '}'
    }
}