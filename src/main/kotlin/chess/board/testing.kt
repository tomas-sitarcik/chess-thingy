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


    var board = initBoard()
    board[1][2] = Piece(PieceType.ROOK, PieceColor.WHITE)
    println(boardString(board))
    //printMoves(rookMoves(intArrayOf(1, 2), board))
    //printMoves(bishopMoves(intArrayOf(1, 3), board))
    printMoves(bishopMoves(intArrayOf(3, 3), board))

}