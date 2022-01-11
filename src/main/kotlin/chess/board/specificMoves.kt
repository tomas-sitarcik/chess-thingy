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

    if (distances[0] != 0) {
        for (i in position[1]..distances[0]) {

        }
    }


}

fun getMovesFromDiagonalDistances( position: IntArray, board: Array<Array<Piece?>>, diagonalDistances: IntArray): Array<IntArray>? {
    TODO("Not yet implemented")
    //generates a list of legal moves according to the diagonal distances
}

