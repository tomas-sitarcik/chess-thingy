package chess.view

import chess.board.Piece
import chess.board.PieceColor
import chess.board.PieceType
import chess.board.initBoard
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color.*
import javafx.scene.text.FontWeight
import tornadofx.*
import javax.swing.text.html.ListView

class MainView : View() {
    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardCanvas : Canvas by fxid("boardCanvas")
    private val pieceCanvas : Canvas by fxid("pieceCanvas")
    private val mouseHighlightCanvas : Canvas by fxid("mouseHighlightCanvas")
    private val whiteMoves : javafx.scene.control.ListView<String> by fxid("listView1")
    private val blackMoves : javafx.scene.control.ListView<String> by fxid("listView2")
    private val gridPane : GridPane by fxid("listView2")

    private val xRatio: Double = 3.0/16.0
    private val yRatio: Double = 1.0/12.0

    private val boardMargin = 0.025
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private val board = initBoard()
    private var activeSide = PieceColor.WHITE


    init {

        //TODO CLEAN UP THIS FILE

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        drawPieces(board)
        drawBoardBackground()

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        pieceCanvas.onMouseClicked = EventHandler {
            print(it.x)
            print(" ")
            println(it.y)

            val coords = determineBoardCoords(it.x, it.y)
            print(coords[0])
            print(", ")
            println(coords[1])
            //highlightSquare(coords, 4)
        }

        pieceCanvas.onMouseMoved = EventHandler {
            wipeHighlightCanvas()
            val coords = determineBoardCoords(it.x, it.y)
            highlightSquare(mouseHighlightCanvas, coords, 4)
        }

    }


    private fun determineBoardCoords(rawX: Double, rawY: Double): IntArray {
        var xActual: Double = rawX - pieceCanvas.width * boardMargin
        var yActual: Double = rawY - pieceCanvas.height * boardMargin

        var xCoord: Int = -1
        var yCoord: Int = -1

        if (xActual / squareSize > 8 || yActual / squareSize > 8 || xActual / squareSize < 0 || yActual / squareSize < 0){
            xActual = -1.0
            yActual = -1.0
        } else {
            xCoord = (xActual / squareSize).toInt()
            yCoord = (yActual / squareSize).toInt()
        }

        return intArrayOf(xCoord, yCoord)
    }

    private fun highlightSquare(canvas: Canvas, coords: IntArray, highlightWidth: Int = 3) {
        if (coords[0] >= 0 && coords[1] >= 0){
            val gCon = canvas.graphicsContext2D
            val origin = canvas.width * boardMargin
            gCon.fill = RED

            gCon.fillRect(origin + (coords[0] * squareSize) - highlightWidth,
                    origin + (coords[1] * squareSize) - highlightWidth,
                    squareSize + highlightWidth * 2,
                    squareSize + highlightWidth * 2)

            gCon.clearRect(origin + (coords[0] * squareSize), origin + (coords[1] * squareSize), squareSize, squareSize)
        }
    }

    private fun wipeHighlightCanvas() {
        mouseHighlightCanvas.graphicsContext2D.clearRect(0.0, 0.0, mouseHighlightCanvas.width, mouseHighlightCanvas.height)
    }

    private fun resizeActions() {
        scaleCanvas(boardCanvas)
        scaleCanvas(pieceCanvas)
        scaleCanvas(mouseHighlightCanvas)
        drawBoardBackground()
        drawPieces(board)
        update()
    }

    private fun drawPieces(board: Array<Array<Piece?>>) {
        //TODO(later) OPTIMIZE MY GOD IS THIS SLOW (lesser priority now that the canvas is being scaled)

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
                PieceType.KING -> Image("file:src/resources/images/pieces/black_king.png")
                PieceType.QUEEN -> Image("file:src/resources/images/pieces/black_queen.png")
                PieceType.ROOK -> Image("file:src/resources/images/pieces/black_rook.png")
                PieceType.KNIGHT -> Image("file:src/resources/images/pieces/black_knight.png")
                PieceType.BISHOP -> Image("file:src/resources/images/pieces/black_bishop.png")
                PieceType.PAWN -> Image("file:src/resources/images/pieces/black_pawn.png")
            }

            PieceColor.WHITE -> when (type) {
                PieceType.KING -> Image("file:src/resources/images/pieces/white_king.png")
                PieceType.QUEEN -> Image("file:src/resources/images/pieces/white_queen.png")
                PieceType.ROOK -> Image("file:src/resources/images/pieces/white_rook.png")
                PieceType.KNIGHT -> Image("file:src/resources/images/pieces/white_knight.png")
                PieceType.BISHOP -> Image("file:src/resources/images/pieces/white_bishop.png")
                PieceType.PAWN -> Image("file:src/resources/images/pieces/white_pawn.png")
            }
        }

    }

    private fun scaleCanvas(canvas: Canvas) {

        val desiredWidth = anchor.width - (anchor.width * xRatio * 2)
        val desiredHeight = anchor.height - (anchor.height * yRatio * 2)

        canvas.scaleX = desiredWidth / canvas.width
        canvas.scaleY = desiredHeight / canvas.height

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

        gCon.fill = GREY
        gCon.fillRect(0.0, 0.0, boardCanvas.width, boardCanvas.height)
        //gCon.fill = WHITE
        //gCon.fillRect(margin, margin, boardBackground.width - 2 * margin, boardBackground.height - 2 * margin)

        for (x in 0..7) {
            for (y in 0..7) {
                if (gCon.fill == WHITE)
                    gCon.fill = GREY
                else gCon.fill = WHITE
                gCon.fillRect((x * squareSize) + margin,
                              (y * squareSize) + margin,
                              squareSize,
                              squareSize)
            }
            if (gCon.fill == WHITE)
                gCon.fill = GREY
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
