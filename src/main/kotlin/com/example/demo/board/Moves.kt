package org.example.board

import org.example.board.Piece

object Moves {
    fun getDistances(position: IntArray): IntArray {
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
        diagonalDistances[1] = Integer.min(distances[0], distances[1]) // down right
        diagonalDistances[2] = Integer.min(distances[0], distances[1]) // down left
        diagonalDistances[3] = Integer.min(distances[0], distances[1]) // top left
        return diagonalDistances
    }

    fun getPossibleMoves(position: IntArray, board: Array<Array<Piece?>>) {
        val xPos = position[1]
        val yPos = position[0]
        val piece = board[yPos][xPos]
    }
}