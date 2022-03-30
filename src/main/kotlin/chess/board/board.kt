package chess.board

import chess.view.MainView
import java.lang.StringBuilder

/** DONT FORGET
 *
 * [
 *   [ROOK, PAWN, ----, ----, ----, ----, PAWN, ROOK]
 *   [KNIGHT, PAWN, ----, ----, ----, ----, PAWN, KNIGHT]
 *   [BISHOP, PAWN, ----, ----, ----, ----, PAWN, BISHOP]
 *   [QUEEN, PAWN, ----, ----, ----, ----, PAWN, KING]
 *   [KING, PAWN, ----, ----, ----, ----, PAWN, QUEEN]
 *   [BISHOP, PAWN, ----, ----, ----, ----, PAWN, BISHOP]
 *   [KNIGHT, PAWN, ----, ----, ----, ----, PAWN, KNIGHT]
 *   [ROOK, PAWN, ----, ----, ----, ----, PAWN, ROOK]
 * ]
 *
 * THIS IS THE REAL SHAPE **/



/** works kinda funky but allows more intuitive access of the pieces with board[x][y] as opposed to having
to use board[y][x] there is probably a better, more elegant way to do this... TOO BAD! **/

fun initBoard(): Array<Array<Piece?>> {
    val board = Array(8) { Array<Piece?>(8) { null } }
    var color = PieceColor.WHITE
    var i = 0
    while (i < 8) {
        for (j in 0..7) { //pawns
            if (i == 0) {
                board[j][i + 1] = Piece(PieceType.PAWN, color)
            } else {
                board[j][i - 1] = Piece(PieceType.PAWN, color)
            }
        }
        //rooks
        board[0][i] = Piece(PieceType.ROOK, color)
        board[7][i] = Piece(PieceType.ROOK, color)

        //knights
        board[1][i] = Piece(PieceType.KNIGHT, color)
        board[6][i] = Piece(PieceType.KNIGHT, color)

        //bishops
        board[2][i] = Piece(PieceType.BISHOP, color)
        board[5][i] = Piece(PieceType.BISHOP, color)

        //royalty
        board[3][i] = Piece(PieceType.QUEEN, color)
        board[4][i] = Piece(PieceType.KING, color)
        color = PieceColor.BLACK
        i += 7
    }

    return board
}

fun getCopyOfBoard(board: Array<Array<Piece?>>): Array<Array<Piece?>> {
    /** get a non-linked copy of a board for storage and other operations **/
    val newBoard = Array(8) { Array<Piece?>(8) { null } }

    for (i in 0..7) {
        for (j in 0..7) {
            newBoard[i][j] = board[i][j]
        }
    }

    return newBoard
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

/** legacy functions (kinda) **/

fun getPrintableBoard(consoleBoard: Array<Array<String>>): String {
    val printableBoard = StringBuilder()
    for (row in consoleBoard) {
        for (square in row) {
            printableBoard.append(square)
            printableBoard.append(" ")
        }
        printableBoard.append("\n")
    }
    return printableBoard.toString()
}

fun getConsoleBoard(board: Array<Array<Piece?>>): Array<Array<String>> {
    val consoleBoard = Array(8) { Array(8) { "" } }
    for (i in 0..7) {
        for (j in 0..7) {
            val squareString = StringBuilder()
            if (board[j][i] != null) {
                squareString.append("[").append(board[j][i]?.type?.name, 0, 2)
                squareString.append(board[j][i]?.color?.name,  0, 2).append("]")
            } else {
                squareString.append("[----]")
            }
            consoleBoard[i][j] = squareString.toString()
        }
    }
    return consoleBoard
}