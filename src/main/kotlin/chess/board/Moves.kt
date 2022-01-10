package chess.board

import chess.board.PieceType.*

    fun getHorizontalDistances(position: Pair<Int, Int>): IntArray {
        val distances = IntArray(4)
        distances[0] = position.first // up
        distances[1] = 7 - position.second // right
        distances[2] = 7 - position.first // down
        distances[3] = position.second // left
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

    fun getMovesFromHorizontalDistances(position: Pair<Int, Int>, distances: IntArray): Array<Pair<Int, Int>> {
        TODO("Not yet implemented")
        //generate a list of moves according to the horizontal distances
    }

    fun getMovesFromDiagonalDistances(position: Pair<Int, Int>, diagonalDistances: IntArray): Array<Pair<Int, Int>> {
        TODO("Not yet implemented")
        //generate a list of moves according to the diagonal distances
    }

fun getPossibleMoves(position: Pair<Int, Int>, board: Array<Array<Piece?>>): Pair<Array<Pair<Int, Int>>, Array<Pair<Int, Int>>>? {
        val piece = board[position.first][position.second]
        val type = piece?.type
        when (type) {
            PAWN -> return pawnMoves(piece, board)
            ROOK -> return rookMoves(piece, board)
            KNIGHT -> return knightMoves(piece, board)
            BISHOP -> return bishopMoves(piece, board)
            QUEEN -> return queenMoves(piece, board)
            KING -> return kingMoves(piece, board)
        }
        return null
    }