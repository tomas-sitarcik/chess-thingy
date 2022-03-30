package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import chess.board.checkCoords
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.BLACK
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

fun getPieceImage(type: PieceType, color: PieceColor): Image {
    //TODO add the actual piece images and their license (LOL)

    var pathString = ""

    /** makes sure the piece images are accessible when running in IDE and when running compiled **/
    pathString =
    if (Image("file:src/main/resources/images/pieces/black_king.png").isError) {
        "file:pieces/"
    } else {
        "file:src/main/resources/images/pieces/"
    }

    return when (color) {

        PieceColor.BLACK -> when (type) {
            PieceType.KING -> Image("${pathString}black_king.png")
            PieceType.QUEEN -> Image("${pathString}black_queen.png")
            PieceType.ROOK -> Image("${pathString}black_rook.png")
            PieceType.KNIGHT -> Image("${pathString}black_knight.png")
            PieceType.BISHOP -> Image("${pathString}black_bishop.png")
            PieceType.PAWN -> Image("${pathString}black_pawn.png")
        }

        PieceColor.WHITE -> when (type) {
            PieceType.KING -> Image("${pathString}white_king.png")
            PieceType.QUEEN -> Image("${pathString}white_queen.png")
            PieceType.ROOK -> Image("${pathString}white_rook.png")
            PieceType.KNIGHT -> Image("${pathString}white_knight.png")
            PieceType.BISHOP -> Image("${pathString}white_bishop.png")
            PieceType.PAWN -> Image("${pathString}white_pawn.png")
        }
    }
}

fun drawPieces(board: Array<Array<Piece?>>, canvas: Canvas, activeSide: PieceColor, boardMargin: Double) {

    /** draws the pieces onto the board, according to the given board, and other properties this is a quite
     *  expensive function - computationally which is why resizing of the board is handled through scaling **/

    var squareSize = (canvas.width - canvas.width * boardMargin * 2) / 8
    val origin = canvas.width * boardMargin
    val gCon = canvas.graphicsContext2D

    gCon.clearRect(0.0, 0.0, canvas.width, canvas.height)

    /** modifies the board depending on the active side **/

    var gBoard = Array(8) { Array<Piece?>(8) { null } }

    for (i in 0..7) {
        for (j in 0..7) {
            gBoard[j][7 - i] = board[j][i]
        }
    }

    for (i in 0..7) {
        for (j in 0..7) {
            if (gBoard[j][i] != null) {
                var temp = gBoard[j][i]
                if (temp != null) {
                    gCon.drawImage(getPieceImage(temp.type, temp.color),
                                                origin + (j * squareSize),
                                                origin + (i * squareSize),
                                                squareSize,
                                                squareSize)
                }
            }
        }
    }

    canvas.toFront() // probably not necessary, but just in case
}

fun drawBoardBackground(canvas: Canvas, fillA: Color, fillB: Color, boardMargin: Double ) {

    val squareSize = (canvas.width - (canvas.width * boardMargin * 2)) / 8
    var margin = canvas.width * boardMargin
    val gCon = canvas.graphicsContext2D

    /** draw the board **/

    gCon.fill = fillB
    gCon.fillRect(0.0, 0.0, canvas.width, canvas.height)
    //gCon.fill = WHITE
    //gCon.fillRect(margin, margin, boardBackground.width - 2 * margin, boardBackground.height - 2 * margin)

    for (x in 0..7) {
        for (y in 0..7) {
            if (gCon.fill == fillA)
                gCon.fill = fillB
            else gCon.fill = fillA
            gCon.fillRect((x * squareSize) + margin,
                    (y * squareSize) + margin,
                    squareSize,
                    squareSize)
        }
        if (gCon.fill == fillA)
            gCon.fill = fillB
        else gCon.fill = fillA
    }

    /** draw the column and row letters/numbers **/

    val columnLetters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H")
    val rowNumbers = arrayOf("1", "2", "3", "4", "5", "6", "7", "8") // in case i decide to actually flip the row numbers
    gCon.fill = Color.BLACK

    gCon.textAlign = TextAlignment.CENTER
    gCon.font = Font(margin * 0.85)

    columnLetters.forEachIndexed { i, letter ->
        gCon.fillText(letter, squareSize * i + margin + squareSize / 2,
                margin - margin * 0.15,
                squareSize)
        gCon.fillText(letter, squareSize * i + margin + squareSize / 2,
                2 * margin + 8 * squareSize - margin * 0.15,
                squareSize)
    }

    rowNumbers.forEachIndexed { i, letter ->
        gCon.fillText(letter, margin / 2,
                squareSize * i + margin + squareSize / 2 + margin * 0.425,
                squareSize)
        gCon.fillText(letter, 2 * margin + 8 * squareSize - margin / 2,
                squareSize * i + margin + squareSize / 2 + margin * 0.425,
                squareSize)
    }
}

fun highlightSquare(canvas: Canvas, coords: IntArray, squareSize: Double, boardMargin: Double) {
    if (checkCoords(coords)){
        val highlightWidth = 3
        val gCon = canvas.graphicsContext2D
        val origin = canvas.width * boardMargin
        gCon.fill = Color.RED

        gCon.fillRect(origin + (coords[0] * squareSize) - highlightWidth,
                origin + (coords[1] * squareSize) - highlightWidth,
                squareSize + highlightWidth * 2,
                squareSize + highlightWidth * 2)

        gCon.clearRect(origin + (coords[0] * squareSize), origin + (coords[1] * squareSize), squareSize, squareSize)
    }
}

fun fillMoves(canvas: Canvas, moves: Array<out IntArray>?, boardMargin: Double, color: Color) {
    val squareSize = (canvas.width - (canvas.width * boardMargin * 2)) / 8
    if (moves != null) {
        for (move in moves) {
            if (checkCoords(move)) {
                val gCon = canvas.graphicsContext2D
                val origin = canvas.width * boardMargin
                gCon.fill = color

                gCon.fillRect(origin + (move[0] * squareSize),
                        origin + (move[1] * squareSize),
                        squareSize, squareSize)
            }
        }
    }
}

fun scaleCanvas(canvas: Canvas, anchor: AnchorPane) {

    /** scales the passed canvas, making sure to not cause weird behaviour if one of them has a negative scale
     *  this also bypasses the need to redraw the pieces every time **/

    val xRatio: Double = 3.0/16.0
    val yRatio: Double = 1.0/12.0

    val desiredWidth = anchor.width - (anchor.width * xRatio * 2)
    val desiredHeight = anchor.height - (anchor.height * yRatio * 2)

    val isYFlipped = canvas.scaleY < 0.0

    canvas.scaleX = desiredWidth / canvas.width
    canvas.scaleY = desiredHeight / canvas.height

    if (canvas.scaleX > canvas.scaleY) {
        canvas.scaleX = canvas.scaleY
    } else {
        canvas.scaleY = canvas.scaleX
    }

    if (canvas.scaleX < 0.0) {
        // just in case!
        canvas.scaleX *= -1.0
    }

    if (isYFlipped) {
        canvas.scaleY *= -1.0
    }
}
