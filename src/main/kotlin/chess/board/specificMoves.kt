package chess.board

// [[x1,y1], [x2, y2], ...]
// legal moves returned will also include capture moves, another method outside of this will take care of
// capture logic, because of the exception that are checks

fun kingMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun queenMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun bishopMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun knightMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun rookMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun pawnMoves(piece: Piece, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun getMovesFromHorizontalDistances(position: IntArray, board: Array<Array<Piece?>>, distances: IntArray): Array<IntArray>? {
    //generates a list of legal moves according to the horizontal distances

    val moves = ArrayList<IntArray>(10)
    if (distances[0] != 0) {
        for (y in distances[0] - 1..position[1]) { // up
            if (board[position[0]][y] == null) {
                moves.add(intArrayOf(0, y))
            } else if (board[position[0]][y] != null) {
                moves.add(intArrayOf(0, y))
            }
        }
    }

    if (distances[1] != 0) {
        for (x in position[0] + 1..distances[1]) { // right            if (board[x][position[1]] == null) {
                moves.add(intArrayOf(0, x))
            } else if (board[x][position[1]] != null) {
                moves.add(intArrayOf(0, x))
            }
        }
    }


}

fun getMovesFromDiagonalDistances( position: IntArray, board: Array<Array<Piece?>>, diagonalDistances: IntArray): Array<IntArray>? {
    TODO("Not yet implemented")
    //generates a list of legal moves according to the diagonal distances
}

