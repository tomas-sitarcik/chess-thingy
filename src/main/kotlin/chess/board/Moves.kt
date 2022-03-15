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

fun filterPossibleMoves(coords: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

    var possibleMoves: Array<out IntArray>? = null

    if (checkCoords(coords)) {
        if (getPiece(coords, board) is Piece) {
            var color = getPiece(coords, board)?.color
            possibleMoves = getPossibleMoves(coords, board)
            if (possibleMoves != null) {
                for (move in possibleMoves) {
                    if (color == getPiece(move, board)?.color) {
                        move[0] = -1
                        move[1] = -1
                    }
                }
            }
        }
    }

    if (possibleMoves == null) {
        println("fuck")
    }

    return possibleMoves
}

fun getAllMovesForColor(color: PieceColor, board: Array<Array<Piece?>>): ArrayList<IntArray> {

    var unfilteredPossibleMoves: ArrayList<IntArray> = arrayListOf()

    // get all the moves
    for (i in 0..7) {
        for (j in 0..7) {
            if (board[j][i] != null) {
                if (board[j][i]?.color != color) {
                    var tempMoves = filterPossibleMoves(intArrayOf(j, i), board)
                    for (move in tempMoves!!) {
                        unfilteredPossibleMoves.add(move)
                    }
                }
            }
        }
    }

    var possibleMoves: ArrayList<IntArray> = arrayListOf()

    for (move in unfilteredPossibleMoves) {
        if (possibleMoves.find { it.contentEquals(move) } == null) {
            possibleMoves.add(move)
        }
    }

    return possibleMoves
}

fun getSafeKingMoves(position: IntArray, board: Array<Array<Piece?>>): Array<IntArray>? {

    //TODO finish the function

    /** this function is called when a check is detected and it will provide the valid moves that will result
     *  in the king being "unchecked" **/
    val kingColor = getPiece(position, board)?.color

    var possibleMoves: ArrayList<IntArray>? = null
    if (kingColor != null) {
        getAllMovesForColor(kingColor, board)
    }

    if (possibleMoves != null) {
        for (move in possibleMoves) {
            print(move[0])
            print(" ")
            println(move[1])
        }
    }


    return possibleMoves?.toTypedArray()

}

fun checkCoords(coords: IntArray): Boolean {
    return coords[0] in 0..7 && coords[1] in 0..7
}
