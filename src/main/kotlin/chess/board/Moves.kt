package chess.board

import chess.board.PieceType.*

fun getHorizontalDistances(position: IntArray): IntArray {
    val distances = IntArray(4)
    distances[0] = position[1] // up
    distances[1] = 7 - position[0] // right
    distances[2] = 7 - position[1] // down
    distances[3] = position[0] // left
    return distances
}

fun getDiagonalDistances(position: IntArray): IntArray {
    val distances = getHorizontalDistances(position)
    val diagonalDistances = IntArray(4)
    diagonalDistances[0] = Integer.min(distances[0], distances[1]) // top right
    diagonalDistances[1] = Integer.min(distances[1], distances[2]) // down right
    diagonalDistances[2] = Integer.min(distances[2], distances[3]) // down left
    diagonalDistances[3] = Integer.min(distances[3], distances[0]) // top left
    return diagonalDistances
}

fun getPossibleMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {
    val piece = board[position[0]][position[1]] ?: return null
    return when (piece.type) {
        PAWN -> pawnMoves(position, board, piece.color)
        ROOK -> rookMoves(position, board)
        KNIGHT -> knightMoves(position, board)
        BISHOP -> bishopMoves(position, board)
        QUEEN -> queenMoves(position, board)
        KING -> kingMoves(position, board)
    }
}
