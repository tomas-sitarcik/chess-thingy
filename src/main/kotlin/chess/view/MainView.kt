package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import chess.board.initBoard
import javafx.beans.value.ObservableValue
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color.*
import javafx.scene.transform.Scale
import tornadofx.*
import javax.swing.text.html.ImageView
import javax.swing.text.html.ListView
import kotlin.Boolean.*
import kotlin.math.round

class MainView : View() {
    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardCanvas : Canvas by fxid("boardCanvas")
    private val pieceCanvas : Canvas by fxid("pieceCanvas")
    private val roundList : ListView by fxid("roundView")

    private val xRatio: Double = 3.0/16.0
    private val yRatio: Double = 1.0/12.0

    private val boardMargin = 0.025
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private val board = initBoard()

    init {

        //TODO eventually fix resizing of the window (lol, lmao)
        //currentStage?.isResizable = false

        drawPieces(board)
        drawBoardBackground()

        val groupScale = Scale()
        boardCanvas.transforms.add(groupScale)
        pieceCanvas.transforms.add(groupScale)

        groupScale.pivotX = anchor.width
        groupScale.pivotY = anchor.height
        groupScale.x = 1.2
        groupScale.y = 1.2

        fun resizeActions() {
            resizeCanvas(boardCanvas)
            scaleCanvas(pieceCanvas)
            drawBoardBackground()
            update()
        }

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, new: Number ->
            //resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            //resizeActions()
        })

    }

    private fun drawPieces(board: Array<Array<Piece?>>) {

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

    private fun initImageViewGrid(pane: Pane) {

        var i = 0
        var imageViewGrid = Array(8) { Array<javafx.scene.image.ImageView?>(8) { null } }

        for (i in 0..7) {
            for (j in 0..7) { //pawns
                if (i == 0) {
                    imageViewGrid[j][i] = javafx.scene.image.ImageView()
                    imageViewGrid[j][i]?.let { pane.getChildList()?.add(it) }
                }
            }
        }
    }

    private fun scaleCanvas(canvas: Canvas) {

        canvas.translateX = anchor.width * xRatio
        canvas.translateY = anchor.height * yRatio

        var desiredWidth = anchor.width - (anchor.width * xRatio)
        var desiredHeight = anchor.height - (anchor.height * yRatio)

        if (desiredWidth > desiredHeight) {
            desiredWidth = desiredHeight

        } else {
            desiredHeight = desiredWidth

        }

        canvas.translateX = anchor.width * 2 / desiredWidth
        canvas.translateY = anchor.height * 2 / desiredHeight

        canvas.scaleX = desiredWidth / canvas.width
        canvas.scaleY = desiredHeight / canvas.height

    }

    private fun resizeCanvas(canvas: Canvas) {

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

    private fun update() {
        margin = boardCanvas.width * boardMargin
        sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
        squareSize = sizeActual / 8
    }

    fun testA() {

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
                gCon.fillRect(  (x * squareSize) + margin,
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