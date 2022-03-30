package chess.board

enum class PieceColor {
    WHITE, BLACK
}

fun flipColor(color: PieceColor): PieceColor {
    //unfortunately couldn't get a companion object with a function to run, which would be quite elegant
    return if (color == PieceColor.BLACK) {
        PieceColor.WHITE
    } else {
        PieceColor.BLACK
    }
}