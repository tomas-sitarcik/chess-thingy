package chess.board

import org.junit.jupiter.api.Assertions.*


internal class SpecificMovesKtTest {

    //TODO later

    @org.junit.jupiter.api.Test
    fun kingMoves() {
        var board = initBoard()
        val correctMoves = arrayOfNulls<IntArray>(8)

        correctMoves[0] = intArrayOf(4, 1)
        correctMoves[1] = intArrayOf(2, 1)
        correctMoves[2] = intArrayOf(5, 2)
        correctMoves[3] = intArrayOf(5, 4)
        correctMoves[4] = intArrayOf(4, 5)
        correctMoves[5] = intArrayOf(2, 5)
        correctMoves[6] = intArrayOf(1, 2)
        correctMoves[7] = intArrayOf(1, 4)

    }

    @org.junit.jupiter.api.Test
    fun queenMoves() {
    }

    @org.junit.jupiter.api.Test
    fun bishopMoves() {
    }

    @org.junit.jupiter.api.Test
    fun knightMoves() {
    }

    @org.junit.jupiter.api.Test
    fun rookMoves() {
    }

    @org.junit.jupiter.api.Test
    fun pawnMoves() {
    }

    @org.junit.jupiter.api.Test
    fun getMovesFromHorizontalDistances() {
    }

    @org.junit.jupiter.api.Test
    fun getMovesFromDiagonalDistances() {
    }
}