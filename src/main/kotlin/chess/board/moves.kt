package chess.board

import chess.board.PieceType.*

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

fun getPiecesOfColor(color: PieceColor, board: Array<Array<Piece?>>): ArrayList<IntArray>? {

    val pieces: ArrayList<IntArray>? = arrayListOf()

    for (i in 0..7){
        for (y in 0..7) {
            val pos = intArrayOf(y, i)
            if (getPiece(pos, board) is Piece) {
                val piece = getPiece(pos, board)
                if (piece?.color == color) {
                    pieces?.add(pos)
                }
            }
        }
    }

    return pieces

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

    return possibleMoves
}

fun getCaptureMoves(color: PieceColor, board: Array<Array<Piece?>>, includePawnMovement: Boolean = false): ArrayList<IntArray> {

    /** returns all capture moves that the color is able to make **/

    var unfilteredPossibleMoves: ArrayList<IntArray> = arrayListOf()

    // get all the moves
    for (i in 0..7) {
        for (j in 0..7) {
            val piece: Piece? = getPiece(intArrayOf(j, i), board)
            if (piece != null) {
                if (piece.color == color) {
                    if (piece.type != PAWN) {
                        val tempMoves = getPossibleMoves(intArrayOf(j, i), board)
                        for (move in tempMoves!!) {
                            if (checkCoords(move)) {
                                unfilteredPossibleMoves.add(move)
                            }
                        }
                    } else {
                        /** adds the capture pawn moves to the possible moves, because they don't function on normal move
                         *  basis, checking if the square they're looking at is null or not is irrelevant in this context
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

                if (includePawnMovement) {
                    if (color == PieceColor.WHITE) {
                        if (i < 7) {
                            if (board[j][i + 1] == null) {
                                unfilteredPossibleMoves.add(intArrayOf(j, i + 1))
                                if (i == 1) {
                                    if (board[j][i + 2] == null) {
                                        unfilteredPossibleMoves.add(intArrayOf(j, i + 2))
                                    }
                                }
                            }
                        }
                    } else {
                        if (i > 0) {
                            if (board[j][i - 1] == null) {
                                unfilteredPossibleMoves.add(intArrayOf(j, i - 1))
                                if (i == 6) {
                                    if (board[j][i - 2] == null) {
                                        unfilteredPossibleMoves.add(intArrayOf(j, i - 2))
                                    }
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

fun getSafeKingMoves(kingPosition: IntArray, boardInput: Array<Array<Piece?>>): ArrayList<IntArray>? {

    /** provides proper check safe movement for the king and a partial check resolving ability - only king move wise **/

    if (getPiece(kingPosition, boardInput)?.type != KING) {
        return null
    }

    val kingColor = getPiece(kingPosition, boardInput)?.color
    val board = getCopyOfBoard(boardInput)
    val kingMoves = getPossibleMoves(kingPosition, board)
    val kingMovesCopy = getPossibleMoves(kingPosition, board)
    var allOpponentMoves: ArrayList<IntArray>? = null
    var possibleMoves: ArrayList<IntArray>? = null
    var illegalMoves: ArrayList<IntArray>?

    board[kingPosition[0]][kingPosition[1]] = null

    if (kingColor != null) {
        allOpponentMoves = getCaptureMoves(flipColor(kingColor), board)
    }

    if (kingMoves != null) {
        illegalMoves = applyMaskToMoves(kingMoves.toCollection(ArrayList()), allOpponentMoves!!)

        possibleMoves = applyMaskToMoves(kingMoves.toCollection(ArrayList()), illegalMoves, true)
    }

    return possibleMoves
}

fun getCheckResolvingMoves(color: PieceColor, board: Array<Array<Piece?>>): ArrayList<IntArray>? {

    /** returns a list of moves that will resolve a check, this list will then be input
     *  into MainView and searched in for showing/trying to make a move **/



    // we don't care where the enemy can move their pawns as it is a non-capturing move - therefore irrelevant here
    val allyMoves = getCaptureMoves(color, board, true)
    val opponentMoves = getCaptureMoves(flipColor(color), board)

    val possibleMoves = applyMaskToMoves(allyMoves, opponentMoves)

    for (position in getPiecesOfColor(flipColor(color), board)!!) {
        possibleMoves?.add(position)
    }

    return possibleMoves
}

fun getKingCoords(color: PieceColor, board: Array<Array<Piece?>>): IntArray {

    for (i in 0..7) {
        for (j in 0..7) {
            if (board[j][i]?.type == KING && board[j][i]?.color == color) {
                return intArrayOf(j, i)
            }
        }
    }

    return intArrayOf(-1, -1)

}

fun getPieceInstances(type: PieceType, color: PieceColor? = null, board: Array<Array<Piece?>>): ArrayList<IntArray> {

    val positions: ArrayList<IntArray> = arrayListOf()

    for (i in 0..7) {
        for (j in 0..7) {
            if (board[j][i]?.type == type && board[j][i]?.color == color || color == null) {
                positions.add(intArrayOf(j, i))
            }
        }
    }

    return positions

}

fun getCastleMoves(color: PieceColor, inputBoard: Array<Array<Piece?>>): Array<IntArray?> {

    var board = getCopyOfBoard(inputBoard)
    val king = getKingCoords(color, board)

    val moves: Array<IntArray?> = arrayOfNulls(6)

    /** returns the move the king can make - index 0 and 1 and also returns the full move at the end - pos and coord for
     *  the rook - i just didn't want to have a bunch of bulky code in the listener because it is already quite full as
     *  it is, and this function already knows the move so there is not a good reason to make another function for it**/

    //TODO move explanation into documentation?

    var x = 0
    if (color == PieceColor.BLACK) {
        x = 7
    }

    if (getPiece(king, board)?.type == KING) {

        if (getPiece(intArrayOf(0, x), board)?.type == ROOK && // queen side castle
            getPiece(intArrayOf(1, x), board) == null &&
            getPiece(intArrayOf(2, x), board) == null &&
            getPiece(intArrayOf(3, x), board) == null) {

            /** first do the rook move and then check for checks **/
            move(intArrayOf(0, x), intArrayOf(3, x), board)
            if (tryMoveOut(king, intArrayOf(2, x), color, board)) {
                moves[0] = intArrayOf(2, x)
                moves[2] = intArrayOf(0, x)
                moves[3] = intArrayOf(3, x)
            }
                board = getCopyOfBoard(inputBoard) // make sure the other check is performed with the initial board

        }

        if (getPiece(intArrayOf(7, x), board)?.type == ROOK && // king side castle
            getPiece(intArrayOf(6, x), board) == null &&
            getPiece(intArrayOf(5, x), board) == null) {

            /** first do the rook move and then check for checks **/
            move(intArrayOf(7, x), intArrayOf(5, x), board)
            if (tryMoveOut(king, intArrayOf(6, x), color, board)) {
                moves[1] = intArrayOf(6, x)
                moves[4] = intArrayOf(7, x)
                moves[5] = intArrayOf(5, x)
            }
        }

    }

    return moves

}

fun checkForCheck(color: PieceColor, board: Array<Array<Piece?>>): Boolean {
    /** too long so its a function now **/
    return getCaptureMoves(flipColor(color), board).find { it.contentEquals(getKingCoords(color, board)) } != null

}

fun applyMaskToMoves(moves: ArrayList<IntArray>, mask: ArrayList<IntArray>?, negative: Boolean = false): ArrayList<IntArray>? {

    /** applies a mask on an arrayList of moves, optionally applies the mask as a negative one **/

    val filteredMoves: ArrayList<IntArray> = arrayListOf()

    if (mask?.size == 0) {
        for (thing in moves) {
            //printMove(thing)
        }
        return moves
    }

    for (move in moves) {
        if (checkCoords(move) && mask != null) {
            for (maskMove in mask) {
                if (move.contentEquals(maskMove) == !negative && filteredMoves.find { it.contentEquals(move) } == null) {
                    if (negative) {
                        if (mask.find { it.contentEquals(move) } == null) {
                            filteredMoves.add(move)
                            break
                        }
                    } else {
                        filteredMoves.add(move)
                        break
                    }
                }
            }
        }
    }

    return filteredMoves
}

fun tryMoveOut(position: IntArray, move: IntArray, color: PieceColor, board: Array<Array<Piece?>>): Boolean {

    /** "checks out" (get it) if the given move would cause the king to get checked **/

    val newBoard = getCopyOfBoard(board)

    move(position, move, newBoard)

    return !checkForCheck(color, newBoard) // im not sure why it has to negated, it just works
}

fun tryTwoMovesOut(position1: IntArray, move1: IntArray, position2: IntArray, move2: IntArray, color: PieceColor, board: Array<Array<Piece?>>): Boolean {

    /** "checks out" (get it) if the given move would cause the king to get checked **/

    val newBoard = getCopyOfBoard(board)

    move(position1, move1, newBoard)
    move(position2, move2, newBoard)

    return !checkForCheck(color, newBoard)

    }

fun checkCoords(coords: IntArray): Boolean {
    return coords[0] in 0..7 && coords[1] in 0..7
}

fun getReadableMove(move: IntArray): String {
    val letters = arrayOf("a", "b", "c", "d", "e", "f", "g", "h")

    return move[1].toString() + letters[move[0]]
}

fun printMove(move: IntArray) {
    print(move[0])
    print(", ")
    println(move[1])
}