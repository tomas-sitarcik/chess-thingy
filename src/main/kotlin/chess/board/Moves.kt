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

fun getMoves(position: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {
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

fun getPossibleMoves(coords: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

    var possibleMoves: Array<out IntArray>? = null

    if (checkCoords(coords)) {
        if (getPiece(coords, board) is Piece) {
            var color = getPiece(coords, board)?.color
            possibleMoves = getMoves(coords, board)
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

fun getAllMovesForColor(color: PieceColor, board: Array<Array<Piece?>>, excludeKing: Boolean = false): ArrayList<IntArray> {

    /** returns all legal moves for all pieces of the given color **/

    var unfilteredPossibleMoves: ArrayList<IntArray> = arrayListOf()

    // get all the moves
    for (i in 0..7) {
        for (j in 0..7) {
            val piece: Piece? = getPiece(intArrayOf(j, i), board)
            if (piece != null) {
                if (piece?.color == color) {
                    val tempMoves = getPossibleMoves(intArrayOf(j, i), board)
                    for (move in tempMoves!!) {
                        if (checkCoords(move)) {
                            unfilteredPossibleMoves.add(move)
                        }
                    }

                    if (piece.type == PAWN) {
                        /** adds the capture moves to the possible moves, because they don't function on normal move
                         *  basis, checks if the square they're looking at is null or not is irrelevant in this context
                         */
                        if (color == PieceColor.WHITE) {
                            if (i < 7){
                                if (j < 7) {
                                    unfilteredPossibleMoves.add(intArrayOf(j + 1, i + 1))
                                }

                                if (j > 0) {
                                    unfilteredPossibleMoves.add(intArrayOf(j - 1, i + 1))
                                }
                            }

                        } else {
                            if (i > 0) {
                                if (j < 7) {
                                    unfilteredPossibleMoves.add(intArrayOf(j + 1, i - 1))
                                }

                                if (j > 0) {
                                    unfilteredPossibleMoves.add(intArrayOf(j - 1, i - 1))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val possibleMoves: ArrayList<IntArray> = arrayListOf()

    for (move in unfilteredPossibleMoves) {
        if (possibleMoves.find { it.contentEquals(move) } == null) {
            possibleMoves.add(move)
        }
    }

    return possibleMoves
}

fun getSafeKingMoves(position: IntArray, boardInput: Array<Array<Piece?>>): Array<IntArray>? {
    //TODO finish the function

    /** CURRENTLY RETURNS THE MOVES THAT ARE NOT VALID
     *  this function is called when a check is detected and it will provide the valid moves that will result
     *  in the king being "unchecked" **/

    if (getPiece(position, boardInput)?.type != KING) {
        return null
    }

    val kingColor = getPiece(position, boardInput)?.color
    var allOpponentMoves: ArrayList<IntArray>? = null

    val board = getCopyOfBoard(boardInput)
    val kingMoves = getPossibleMoves(position, board)

    board[position[0]][position[1]] = null

    var possibleMoves: ArrayList<IntArray>? = null
    if (kingColor != null) {
        allOpponentMoves = getAllMovesForColor(flipColor(kingColor), board)
    }

    if (kingMoves != null) {
        possibleMoves = applyMaskToMoves(applyMaskToMoves(allOpponentMoves, kingMoves), kingMoves)
    }

    return possibleMoves?.toTypedArray()
}

fun applyMaskToMoves(moves: ArrayList<IntArray>?, mask: Array<out IntArray>, negative: Boolean = false): ArrayList<IntArray>? {
    var filteredMoves: ArrayList<IntArray>? = arrayListOf()

    if (moves != null){
        for (move in moves) {
            for (maskMove in mask) {
                if (checkCoords(maskMove)) {
                    if (move.contentEquals(maskMove) == !negative) {
                        filteredMoves?.add(move)
                    }
                }
            }
        }
    }

    return filteredMoves
}


fun checkCoords(coords: IntArray): Boolean {
    return coords[0] in 0..7 && coords[1] in 0..7
}

fun printMove(move: IntArray) {
    print(move[0])
    print(", ")
    println(move[1])
}
