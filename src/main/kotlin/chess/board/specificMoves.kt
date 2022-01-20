package chess.board

import chess.board.PieceColor.*

// [[x1,y1], [x2, y2], ...]
// legal moves returned will also include capture moves, another method outside of this will take care of
// capture logic, because of the exception that are checks and en passants

fun kingMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {
    val horizontalDistances = getHorizontalDistances(position)
    val diagonalDistances = getDiagonalDistances(position)
    val moves = ArrayList<IntArray>(8)
    val x = position[0]
    val y = position[1]

    if (horizontalDistances[0] > 0) { // up
        moves.add(intArrayOf(x, y - 1))
    }

    if (horizontalDistances[1] > 0) { // right
        moves.add(intArrayOf(x + 1, y ))
    }

    if (horizontalDistances[2] > 0) { // down
        moves.add(intArrayOf(x, y + 1))
    }

    if (horizontalDistances[3] > 0) { // left
        moves.add(intArrayOf(x - 1, y))
    }


    if (diagonalDistances[0] > 0) { // top right
        moves.add(intArrayOf(x + 1, y - 1))
    }

    if (diagonalDistances[1] > 0) { // bottom right
        moves.add(intArrayOf(x + 1, y + 1))
    }

    if (diagonalDistances[2] > 0) { // bottom left
        moves.add(intArrayOf(x - 1, y + 1))
    }

    if (diagonalDistances[3] > 0) { // top left
        moves.add(intArrayOf(x - 1, y - 1))
    }

    return moves.toTypedArray()
}

fun queenMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {
    return getMovesFromHorizontalDistances(position, board)?.
    plus(getMovesFromDiagonalDistances(position, board))
}

fun bishopMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray> {
    return getMovesFromDiagonalDistances(position, board)
}

fun knightMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

    val distances = getHorizontalDistances(position)
    val moves = ArrayList<IntArray>(8)
    var temp: IntArray

    // top
    if (distances[0] >= 2) {

        if (distances[1] > 0) {
            moves.add(intArrayOf(position[0] + 1, position[1] - 2))
        }

        if (distances[3] > 0) {
            moves.add(intArrayOf(position[0] - 1, position[1] - 2))
        }

    }

    // right
    if (distances[1] >= 2) {

        if (distances[0] > 0) {
            moves.add(intArrayOf(position[0] + 2, position[1] - 1))
        }

        if (distances[2] > 0) {
            moves.add(intArrayOf(position[0] + 2, position[1] + 1))
        }

    }

    // down
    if (distances[2] >= 2) {

        if (distances[1] > 0) {
            moves.add(intArrayOf(position[0] + 1, position[1] + 2))
        }

        if (distances[3] > 0) {
            moves.add(intArrayOf(position[0] - 1, position[1] + 2))
        }

    }

    // left
    if (distances[3] >= 2) {

        if (distances[0] > 0) {
            moves.add(intArrayOf(position[0] - 2, position[1] - 1))
        }

        if (distances[2] > 0) {
            moves.add(intArrayOf(position[0] - 2, position[1] + 1))
        }

    }

    return moves.toTypedArray()

}

fun rookMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {
    return getMovesFromHorizontalDistances(position, board)
}

// en passant capturing not handled here :D
fun pawnMoves(position: IntArray, board: Array<Array<Piece?>>, color: PieceColor): Array<out IntArray>? {
    val moves = ArrayList<IntArray>(4)
    val x = position[0]
    val y = position[1]

    // double move
    if (color == WHITE) {
        if (y < 7) {
            if (board[x][y + 1] == null) {
                moves.add(intArrayOf(x, y + 1))
                if (board[x][y + 2] == null && y == 1) {
                    moves.add(intArrayOf(x, y + 2))
                }
            }
        }
    } else {
        if (y > 0) {
            if (board[x][y - 1] == null) {
                moves.add(intArrayOf(x, y - 1))
                if (board[x][y - 2] == null && y == 6) {
                    moves.add(intArrayOf(x, y - 2))
                }
            }
        }
    }

    // captures
    if (color == WHITE) {
        if (y < 7){
            if (board[x + 1][y + 1] != null){
                moves.add(intArrayOf(x + 1, y + 1))
            }
            if (board[x - 1][y + 1] != null){
                moves.add(intArrayOf(x - 1, y + 1))
            }
        }

    } else {
        if (y > 0) {
            if (board[x + 1][y - 1] != null){
                moves.add(intArrayOf(x + 1, y - 1))
            }
            if (board[x - 1][y - 1] != null){
                moves.add(intArrayOf(x - 1, y - 1))
            }
        }
    }
    return moves.toTypedArray()

}

//generates a list of legal moves according to the horizontal distances
fun getMovesFromHorizontalDistances(
    position: IntArray,
    board: Array<Array<Piece?>>):
        Array<IntArray>? {

    val distances = getHorizontalDistances(position)
    val moves = ArrayList<IntArray>(14)
    var pos: Int
    var add: Boolean

    // up
    if (distances[0] != 0) {
        pos = position[0]
        for (y in position[1] - 1 downTo 0) {
            add = moves.add(intArrayOf(pos, y))
            if (board[pos][y] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    // right
    if (distances[1] != 0) {
        pos = position[1]
        for (x in position[0] + 1 .. 7) {
            add = moves.add(intArrayOf(x, pos))
            if (board[x][pos] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    // down
    if (distances[2] != 0) {
        pos = position[0]
        for (y in position[1] + 1 .. 7 ) {
            add = moves.add(intArrayOf(pos, y))
            if (board[pos][y] == null) {
                add
            } else {
                add
                break
            }
        }
    }

    // left
    if (distances[3] != 0) {
        pos = position[1]
        for (x in position[0] - 1 downTo 0) {
            add = moves.add(intArrayOf(x, pos))
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

//generates a list of legal moves according to the diagonal distances
fun getMovesFromDiagonalDistances(
    position: IntArray,
    board: Array<Array<Piece?>>):
        Array<out IntArray> {


    val distances = getDiagonalDistances(position)
    val moves = ArrayList<IntArray>(14)
    var add: Boolean
    var x = position[0]
    var y = position[1]

    // top right
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

    // down right
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

    // down left
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

    // top left
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

