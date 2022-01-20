package chess.board

import chess.board.SpecificMovesKtTest

fun main(args: Array<String>) {

    fun printMoves(moves: Array<out IntArray>?) {
        if (moves != null) {
            for (move in moves) {
                print(move[0])
                print(", ")
                println(move[1])
            }
        }
    }

    fun visualizeMoves(consoleBoard: Array<Array<String>>, moves: Array<out IntArray>?) {
        if (moves != null) {
            for (move in moves) {
                consoleBoard[move[1]][move[0]] = "[XXXX]"
            }
        }
    }

    fun printDistances(distances: IntArray) {
        for (distance in distances) {
            print(distance)
            print(", ")
        }
        print("\n")
    }

    var board = initBoard()
    board[2][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    board[3][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    board[4][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    //println(getPrintableBoard(getConsoleBoard(board)))
    //printMoves(rookMoves(intArrayOf(1, 2), board))
    //printMoves(bishopMoves(intArrayOf(1, 3), board))
    //printMoves(bishopMoves(intArrayOf(3, 3), board))

    var consBoard = getConsoleBoard(board)
    println(getPrintableBoard(consBoard))
    //visualizeMoves(consBoard, pawnMoves(intArrayOf(3, 6), board, PieceColor.BLACK))
    //printMoves(knightMoves(intArrayOf(4, 7), board))
    visualizeMoves(consBoard, knightMoves(intArrayOf(6, 7), board))
    println(getPrintableBoard(consBoard))

    val test = SpecificMovesKtTest()

    print(test.moveSort(pawnMoves(intArrayOf(3, 6), board, PieceColor.WHITE)))

}