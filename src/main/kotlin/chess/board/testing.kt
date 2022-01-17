package chess.board

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
    board[1][2] = Piece(PieceType.ROOK, PieceColor.WHITE)
    println(getPrintableBoard(getConsoleBoard(board)))
    //printMoves(rookMoves(intArrayOf(1, 2), board))
    //printMoves(bishopMoves(intArrayOf(1, 3), board))
    printMoves(bishopMoves(intArrayOf(3, 3), board))

    var consBoard = getConsoleBoard(board)
    println(getPrintableBoard(consBoard))
    visualizeMoves(consBoard, kingMoves(intArrayOf(3, 3), board))
    println(getPrintableBoard(consBoard))

}