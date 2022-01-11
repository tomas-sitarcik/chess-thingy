package chess.board

import java.lang.StringBuilder

fun boardString(board: Array<Array<Piece?>>): String {
    val consoleBoard = StringBuilder()

    // works kinda funky but allows access of the pieces with board[x][y] as opposed to having to use board[y][x]
    // there is probably a better more elegant way to do this... TOO BAD!
    for (i in 0..7) {
        for (j in 0..7) {
            if (board[j][i] != null) {
                consoleBoard.append("[").append(board[j][i]?.type?.name, 0, 2)
                consoleBoard.append(board[j][i]?.color?.name, 0, 2).append("] ")
            } else {
                consoleBoard.append("[----] ")
            }
        }
        consoleBoard.append("\n")
    }

    // old bad and smelly code
    /*for (row in board) {
        for (piece in row) {
            if (piece != null) {
                consoleBoard.append("[").append(piece.type.name, 0, 2)
                consoleBoard.append(piece.color.name, 0, 2).append("] ")
            } else {
                consoleBoard.append("[----] ")
            }
        }
    }*/

    return consoleBoard.toString()
}

fun initBoard(): Array<Array<Piece?>> {
    var board = Array(8) { Array<Piece?>(8) { i -> null } }
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
