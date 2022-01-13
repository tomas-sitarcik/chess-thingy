package chess.board

// [[x1,y1], [x2, y2], ...]
// legal moves returned will also include capture moves, another method outside of this will take care of
// capture logic, because of the exception that are checks

fun kingMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun queenMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun bishopMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun knightMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun rookMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun pawnMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray> {
    TODO("Not yet implemented")
}

fun getMovesFromHorizontalDistances(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray>? {
    //generates a list of legal moves according to the horizontal distances

    val distances = getHorizontalDistances(position)
    val moves = ArrayList<IntArray>(14)
    var pos: Int
    var add: Boolean

    if (distances[0] != 0) {
        pos = position[0]
        for (y in position[1] - 1 downTo 0) { // up
            add = moves.add(intArrayOf(pos, y))
            if (board[pos][y] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[1] != 0) {
        pos = position[1]
        for (x in position[0] + 1 .. 7) { // right
            add = moves.add(intArrayOf(pos, x))
            if (board[x][pos] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[2] != 0) {
        pos = position[0]
        for (y in position[1] + 1 .. 7 ) { // down
            add = moves.add(intArrayOf(pos, y))
            if (board[pos][y] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[3] != 0) {
        pos = position[1]
        for (x in position[0] - 1 downTo 0) { // left
            add = moves.add(intArrayOf(pos, x))
            if (board[x][pos] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    return moves.toTypedArray()
}

fun getMovesFromDiagonalDistances( position: IntArray, board: Array<Array<Piece?>>): Array<IntArray>? {
    //generates a list of legal moves according to the diagonal distances

    val distances = getDiagonalDistances(position)
    val moves = ArrayList<IntArray>(14)
    var pos: Int
    var add: Boolean
    var x = position[0]
    var y = position[1]


    if (distances[0] != 0) {
        for (d in 1 .. distances[0]){
            add = moves.add(intArrayOf(x + d, y - d))
            if (board[x + d][y - d] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[1] != 0) {
        for (d in 1 .. distances[1]){
            add = moves.add(intArrayOf(x + d, y + d))
            if (board[x + d][y + d] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[2] != 0) {
        for (d in 1 .. distances[2]){
            add = moves.add(intArrayOf(x - d, y + d))
            if (board[x - d][y + d] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    if (distances[3] != 0) {
        for (d in 1 .. distances[3]){
            add = moves.add(intArrayOf(x - d, y - d))
            if (board[x - d][y - d] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    return moves.toTypedArray()
}

