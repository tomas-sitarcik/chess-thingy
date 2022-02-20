package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import chess.board.initBoard
import javafx.beans.value.ObservableValue
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color.*
import tornadofx.*

class MainView : View() {
    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardCanvas : Canvas by fxid("boardCanvas")
    private val pieceCanvas : Canvas by fxid("pieceCanvas")

    private val boardMargin = 0.025
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private val board = initBoard()

    init {

        //currentStage?.isResizable = false
        //currentStage?.minWidth = 800.0
        //currentStage?.minHeight = 639.0
        //currentStage?.width = 800.0
        //currentStage?.height = 639.0

        //boardBackground.height = 500.0
        //boardBackground.width = 500.0

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, new: Number ->
            resizeCanvas(boardCanvas)
            resizeCanvas(pieceCanvas)
            drawPieces(board)
            drawBoardBackground()
        })

        anchor.heightProperty().addListener(ChangeListener {
                _: ObservableValue<out Number>?, _: Number, new: Number ->
            resizeCanvas(boardCanvas)
            resizeCanvas(pieceCanvas)
            drawPieces(board)
            drawBoardBackground()
        })

    }

    private fun drawPieces(board: Array<Array<Piece?>>) {

        //TODO OPTIMIZE MY GOD IS THIS SLOW

        val origin = boardCanvas.width * boardMargin
        val gCon = pieceCanvas.graphicsContext2D

        gCon.clearRect(0.0, 0.0, boardCanvas.width, boardCanvas.height)

        for (i in 0..7) {
            for (j in 0..7) {
                if (board[j][i] != null) {
                    var temp = board[j][i]
                    if (temp != null) {
                        gCon.drawImage(getPieceImage(temp.type, temp.color), origin + (j * squareSize), origin + (i * squareSize), squareSize, squareSize)
                    }
                }
            }
        }

        pieceCanvas.toFront()
    }

    private fun getPieceImage(type: PieceType, color: PieceColor): Image {
        //TODO add the actual piece graphics and their license (LOL)

        return when (color) {

            PieceColor.BLACK -> when (type) {
                PieceType.KING -> Image("file:src/resources/images/pieces/reimuBlack.png")
                PieceType.QUEEN -> Image("file:src/resources/images/pieces/reimuBlack.png")
                PieceType.ROOK -> Image("file:src/resources/images/pieces/reimuBlack.png")
                PieceType.KNIGHT -> Image("file:src/resources/images/pieces/reimuBlack.png")
                PieceType.BISHOP -> Image("file:src/resources/images/pieces/reimuBlack.png")
                PieceType.PAWN -> Image("file:src/resources/images/pieces/reimuBlack.png")
            }

            PieceColor.WHITE -> when (type) {
                PieceType.KING -> Image("file:src/resources/images/pieces/reimu.png")
                PieceType.QUEEN -> Image("file:src/resources/images/pieces/reimu.png")
                PieceType.ROOK -> Image("file:src/resources/images/pieces/reimu.png")
                PieceType.KNIGHT -> Image("file:src/resources/images/pieces/reimu.png")
                PieceType.BISHOP -> Image("file:src/resources/images/pieces/reimu.png")
                PieceType.PAWN -> Image("file:src/resources/images/pieces/reimu.png")
            }
        }

    }

    private fun resizeCanvas(canvas: Canvas) {

        val xRatio: Double = 3.0/16.0
        val yRatio: Double = 1.0/12.0

        // there DEFINITELY is a better way to do this ...too bad!
        canvas.translateX = anchor.width * xRatio
        canvas.translateY = anchor.height * yRatio

        canvas.width = anchor.width - (anchor.width * xRatio * 2)
        canvas.height = anchor.height - (anchor.height * yRatio * 2)

        if (canvas.width > canvas.height) {
            canvas.width = canvas.height
            canvas.translateX = anchor.width / 2 - canvas.width / 2
        } else {
            canvas.height = canvas.width
            canvas.translateY = anchor.height / 2 - canvas.height / 2
        }
    }

    fun testA() {
        println("anchor")
        println("width " + anchor.width)
        println("height " + anchor.height)

        println("board")
        println("width " + boardCanvas.width)
        println("height " + boardCanvas.height)
    }

    private fun drawBoardBackground() {

        margin = boardCanvas.width * boardMargin
        sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
        squareSize = sizeActual / 8

        val gCon = boardCanvas.graphicsContext2D

        gCon.fill = BLACK
        gCon.fillRect(0.0, 0.0, boardCanvas.width, boardCanvas.height)
        //gCon.fill = WHITE
        //gCon.fillRect(margin, margin, boardBackground.width - 2 * margin, boardBackground.height - 2 * margin)

        for (x in 0..7) {
            for (y in 0..7) {
                if (gCon.fill == WHITE)
                    gCon.fill = BLACK
                else gCon.fill = WHITE
                gCon.fillRect(
                                (x * squareSize) + margin,
                                (y * squareSize) + margin,
                                squareSize,
                                squareSize
                            )
            }
            if (gCon.fill == WHITE)
                gCon.fill = BLACK
            else gCon.fill = WHITE
        }
    }

    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }

}