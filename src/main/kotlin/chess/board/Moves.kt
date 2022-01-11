package chess.board

import chess.board.PieceType.*

fun getHorizontalDistances(position: IntArray): IntArray {
    val distances = IntArray(4)
    distances[0] = position[0] // up
    distances[1] = 7 - position[1] // right
    distances[2] = 7 - position[0] // down
    distances[3] = position[1] // left
    return distances
}

fun getDiagonalDistances(distances: IntArray): IntArray {
    val diagonalDistances = IntArray(4)
    diagonalDistances[0] = Integer.min(distances[0], distances[1]) // top right
    diagonalDistances[1] = Integer.min(distances[1], distances[2]) // down right
    diagonalDistances[2] = Integer.min(distances[2], distances[3]) // down left
    diagonalDistances[3] = Integer.min(distances[3], distances[0]) // top left
    return diagonalDistances
}

fun getPossibleMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray>? {
    val piece = board[position[0]][position[1]]
    when (piece?.type) {
            PAWN -> return pawnMoves(piece, board)
            ROOK -> return rookMoves(piece, board)
            KNIGHT -> return knightMoves(piece, board)
            BISHOP -> return bishopMoves(piece, board)
            QUEEN -> return queenMoves(piece, board)
            KING -> return kingMoves(piece, board)
        }
    return null
    }
