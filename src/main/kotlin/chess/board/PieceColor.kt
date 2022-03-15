package chess.board

enum class PieceColor {
    WHITE, BLACK
}

fun flipColor(color: PieceColor): PieceColor {
    return if (color == PieceColor.BLACK) {
        PieceColor.WHITE
    } else {
        PieceColor.BLACK
    }
}