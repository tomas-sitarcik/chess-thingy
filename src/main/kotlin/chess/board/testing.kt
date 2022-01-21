package chess.board

//import com.sun.org.slf4j.internal.Logger
//import com.sun.org.slf4j.internal.LoggerFactory
import mu.KotlinLogging
import org.slf4j.LoggerFactory


fun main(args: Array<String>) {

    val logger = KotlinLogging.logger {}

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

    fun visualizeMoves(position: IntArray, board: Array<Array<Piece?>>): Unit? {
        val moves = getPossibleMoves(position, board)
        if (moves == null) {
            logger.trace ("Visuallization of moves failed" )
                return null
            }
        var consBoard = getConsoleBoard(board)
        println(getPrintableBoard(consBoard))
        printMoves(moves)
        visualizeMoves(consBoard, moves)
        println(getPrintableBoard(consBoard))
        return null
    }

    fun getPiece(position: IntArray, board: Array<Array<Piece?>>): Piece? {
        return board[position[0]][position[1]]
    }

    fun setPiece(position: IntArray, piece: Piece?, board: Array<Array<Piece?>>) {
        board[position[0]][position[1]] = piece
    }

    fun move(position: IntArray, destination: IntArray, board: Array<Array<Piece?>>) {
        val piece = getPiece(position, board)
        setPiece(destination, piece, board)
        setPiece(position, null, board)
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
    visualizeMoves(intArrayOf(3, 4), board)


}