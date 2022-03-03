package chess.view

import chess.board.*
import chess.game.*
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*
import tornadofx.*

class MainView() : View() {

    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    val boardCanvas : Canvas by fxid("boardCanvas")
    val pieceCanvas : Canvas by fxid("pieceCanvas")
    private val mouseHighlightCanvas : Canvas by fxid("mouseHighlightCanvas")
    val moveHighlightCanvas : Canvas by fxid("moveHighlightCanvas")
    val whiteMoves : javafx.scene.control.ListView<String> by fxid("listView1")
    val blackMoves : javafx.scene.control.ListView<String> by fxid("listView2")

    val boardMargin = 0.025
    var margin = boardCanvas.width * boardMargin
    var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    var squareSize = sizeActual / 8
    val mainBoard = initBoard()
    var activeSide = PieceColor.BLACK
    var activeSquare: IntArray? = null
    var validMoves: Array<out IntArray>? = null
    val canvasArray = arrayOf(boardCanvas, pieceCanvas, mouseHighlightCanvas, moveHighlightCanvas)

    private val canvasMap: Map<String, Canvas> =
            mapOf<String, Canvas>(
                                    "board" to boardCanvas,
                                    "pieces" to pieceCanvas,
                                    "highlights" to mouseHighlightCanvas,
                                    "moves" to moveHighlightCanvas
                                 )


    init {

        //TODO CLEAN UP THIS FILE

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        drawPieces(mainBoard, this)
        drawBoardBackground(this)

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })


        pieceCanvas.onMouseClicked = EventHandler {
            clickEvents(it, this)
            drawPieces(mainBoard, this)
        }

        pieceCanvas.onMouseMoved = EventHandler {
            wipeCanvas(mouseHighlightCanvas)
            val coords = determineBoardCoords(it.x, it.y, view = this)
            highlightSquare(mouseHighlightCanvas, coords, 4, this)
            //fillSquare(mouseHighlightCanvas, coords, rgb(0, 255, 125, 0.5))
        }

    }



    @JvmName("setActiveSquare1")
    fun setActiveSquare(coords: IntArray) {
        activeSquare = coords
        fillSquare(moveHighlightCanvas, coords, rgb(255, 255, 0, 0.5), this)
    }

    fun unsetActiveSquare() {
        activeSquare = null
        wipeCanvas(moveHighlightCanvas)
    }

    private fun resizeActions() {
        scaleCanvas(boardCanvas, anchor)
        scaleCanvas(pieceCanvas, anchor)
        scaleCanvas(mouseHighlightCanvas, anchor)
        scaleCanvas(moveHighlightCanvas, anchor)
        drawBoardBackground(this)
        drawPieces(mainBoard, this)
        update()
    }

    fun wipeCanvas(canvas: Canvas) {
        canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
    }



    private fun update() {
        margin = boardCanvas.width * boardMargin
        sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
        squareSize = sizeActual / 8
    }

    fun switchSides() {
        activeSide = if (activeSide == PieceColor.BLACK) {
            PieceColor.WHITE
        } else {
            PieceColor.BLACK
        }
        moveHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1.0
        mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1.0

        drawPieces(mainBoard, this)
        wipeCanvas(moveHighlightCanvas)
        unsetActiveSquare()
    }

    fun testA() {
        switchSides()


        println("pieces")
        println("width " + pieceCanvas.width)
        println("height " + pieceCanvas.height)

        println("board")
        println("width " + boardCanvas.width)
        println("height " + boardCanvas.height)
    }



    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }

    fun revertToRound(){

    }

}
