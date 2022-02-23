package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import chess.board.initBoard
import javafx.beans.value.ObservableValue
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color.*
import tornadofx.*
import java.awt.event.KeyEvent
import java.beans.EventHandler
import javax.swing.text.html.ListView

class MainView : View() {
    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardCanvas : Canvas by fxid("boardCanvas")
    private val pieceCanvas : Canvas by fxid("pieceCanvas")
    private val roundList : ListView by fxid("roundView")
    private val gridPane : GridPane by fxid("gridPane")

    private val xRatio: Double = 3.0/16.0
    private val yRatio: Double = 1.0/12.0

    private val boardMargin = 0.025
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private val board = initBoard()
    private val activeSide = PieceColor.WHITE

    init {

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        drawPieces(board)
        drawBoardBackground()

        fun resizeActions() {
            scaleCanvas(boardCanvas)
            scaleCanvas(pieceCanvas)
            drawBoardBackground()
            //drawPieces(board)
            update()
        }

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, new: Number ->
            resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        pieceCanvas.addEventHandler(KeyEvent)

    }

    private fun drawPieces(board: Array<Array<Piece?>>) {
        //TODO(later) OPTIMIZE MY GOD IS THIS SLOW

        val origin = pieceCanvas.width * boardMargin
        val gCon = pieceCanvas.graphicsContext2D

        gCon.clearRect(0.0, 0.0, pieceCanvas.width, pieceCanvas.height)

        var gBoard = Array(8) { Array<Piece?>(8) { null } }
        if (activeSide == PieceColor.WHITE) { // reverses the board and makes the active side be the one on the bottom
            for (i in 0..7) {
                for (j in 0..7) {
                    gBoard[j][7 - i] = board[j][i]
                }
            }
        }


        for (i in 0..7) {
            for (j in 0..7) {
                if (gBoard[j][i] != null) {
                    var temp = gBoard[j][i]
                    if (temp != null) {
                        gCon.drawImage(getPieceImage(temp.type, temp.color), origin + (j * squareSize), origin + (i * squareSize), squareSize, squareSize)
                    }
                }
            }
        }

        pieceCanvas.toFront()
    }

    private fun getPieceImage(type: PieceType, color: PieceColor): Image {
        //TODO add the actual piece images and their license (LOL)

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

    private fun scaleCanvas(canvas: Canvas) {

        val desiredWidth = anchor.width - (anchor.width * xRatio * 2)
        val desiredHeight = anchor.height - (anchor.height * yRatio * 2)

        canvas.scaleX = desiredWidth / 500
        canvas.scaleY = desiredHeight / 500

        if (canvas.scaleX > canvas.scaleY) {
            canvas.scaleX = canvas.scaleY
        } else {
            canvas.scaleY = canvas.scaleX
        }

    }

    private fun update() {
        margin = boardCanvas.width * boardMargin
        sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
        squareSize = sizeActual / 8
    }


    fun testA() {

        drawPieces(board)

        println("pieces")
        println("width " + pieceCanvas.width)
        println("height " + pieceCanvas.height)

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
                gCon.fillRect((x * squareSize) + margin,
                              (y * squareSize) + margin,
                              squareSize,
                              squareSize)
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

    fun revertToRound(){

    }

}