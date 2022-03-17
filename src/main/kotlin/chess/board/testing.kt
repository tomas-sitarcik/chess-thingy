package chess.board

import java.io.File

fun printBoard(board: Array<Array<Piece?>>) {
    println(getPrintableBoard(getConsoleBoard(board)))
}

fun main(args: Array<String>) {

    val file = File("src/resources/MainView.fxml")
    println(file.name)

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

    fun printMoves(position: IntArray, board: Array<Array<Piece?>>): Unit? {
        val moves = getMoves(position, board)
        if (moves == null) {
            print("failed")
            return null
            }

        val consBoard = getConsoleBoard(board)
        println(getPrintableBoard(consBoard))
        //printMoves(moves)
        visualizeMoves(consBoard, moves)
        println(getPrintableBoard(consBoard))
        return null
    }


    var board = initBoard()
    //board[2][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    //board[3][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    //board[4][5] = Piece(PieceType.ROOK, PieceColor.WHITE)
    //println(getPrintableBoard(getConsoleBoard(board)))
    //printMoves(rookMoves(intArrayOf(1, 2), board))
    //printMoves(bishopMoves(intArrayOf(1, 3), board))
    //printMoves(bishopMoves(intArrayOf(3, 3), board))

    /*
    var consBoard = getConsoleBoard(board)
    println(getPrintableBoard(consBoard))
    visualizeMoves(consBoard, pawnMoves(intArrayOf(3, 6), board, PieceColor.BLACK))
    printMoves(knightMoves(intArrayOf(3, 3), board))
    visualizeMoves(consBoard, kingMoves(intArrayOf(3, 3), board))
    println(getPrintableBoard(consBoard))
     */

    //visualizeMoves(intArrayOf(3, 7), board)



    move(intArrayOf(3, 7), intArrayOf(3, 3), board)
    printMoves(intArrayOf(3, 3), board)



}
