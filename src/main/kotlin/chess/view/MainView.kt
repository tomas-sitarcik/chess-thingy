package chess.view

import chess.board.*
import chess.game.*
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
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
    val canv = CanvasHandler(this)
    val game = Game(this, canv)

    private val canvasMap: Map<String, Canvas> =
            mapOf<String, Canvas>(
                                    "board" to boardCanvas,
                                    "pieces" to pieceCanvas,
                                    "highlights" to mouseHighlightCanvas,
                                    "moves" to moveHighlightCanvas
                                 )


    init {

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0



        canv.drawPieces(mainBoard)
        canv.drawBoardBackground()

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })


        pieceCanvas.onMouseClicked = EventHandler {
            game.clickEvents(it)
            canv.drawPieces(mainBoard)
        }

        pieceCanvas.onMouseMoved = EventHandler {
            wipeCanvas(mouseHighlightCanvas)
            val coords = game.determineBoardCoords(it.x, it.y)
            canv.highlightSquare(mouseHighlightCanvas, coords, 4)
            //fillSquare(mouseHighlightCanvas, coords, rgb(0, 255, 125, 0.5))
        }

    }



    @JvmName("setActiveSquare1")
    fun setActiveSquare(coords: IntArray) {
        activeSquare = coords
        canv.fillSquare(moveHighlightCanvas, coords, rgb(255, 255, 0, 0.5))
    }

    fun unsetActiveSquare() {
        activeSquare = null
        wipeCanvas(moveHighlightCanvas)
    }

    private fun resizeActions() {

        for (canvas in canvasArray) {
            canv.scaleCanvas(canvas, anchor)
        }
        canv.drawBoardBackground()
        canv.drawPieces(mainBoard)
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

        canv.drawPieces(mainBoard)
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
