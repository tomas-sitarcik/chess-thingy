package chess.board

import java.lang.StringBuilder

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

// works kinda funky but allows more intuitive access of the pieces with board[x][y] as opposed to having
// to use board[y][x] there is probably a better, more elegant way to do this... TOO BAD!

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
