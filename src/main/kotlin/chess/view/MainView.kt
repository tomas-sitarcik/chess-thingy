package chess.view

import chess.board.*
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*
import tornadofx.*

class MainView : View() {

    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardCanvas : Canvas by fxid("boardCanvas")
    private val pieceCanvas : Canvas by fxid("pieceCanvas")
    private val mouseHighlightCanvas : Canvas by fxid("mouseHighlightCanvas")
    private val moveHighlightCanvas : Canvas by fxid("moveHighlightCanvas")
    private val whiteMoves : javafx.scene.control.ListView<String> by fxid("listView1")
    private val blackMoves : javafx.scene.control.ListView<String> by fxid("listView2")

    private val xRatio: Double = 3.0/16.0
    private val yRatio: Double = 1.0/12.0

    private val boardMargin = 0.025
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private var mainBoard = initBoard()
    private var activeSide = PieceColor.WHITE
    private var activeSquare: IntArray? = null
    private var validMoves: Array<out IntArray>? = null

    private var turnStates: MutableMap<Int, Array<Array<Piece?>>> = mutableMapOf()
    private var turnCount: Int = 0


    init {

        //TODO CLEAN UP THIS FILE

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
        drawBoardBackground()

        turnStates[0] = getCopyOfBoard(mainBoard)

        anchor.widthProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, _: Number ->
            resizeActions()
        })


        pieceCanvas.onMouseClicked = EventHandler {

            val coords = determineBoardCoords(it.x, it.y)
            var isPieceActiveColor: Boolean = false
            if (checkCoords(coords)) {
                isPieceActiveColor = getPiece(coords, mainBoard)?.color == activeSide
            }
            var validMove = false
            if (validMoves != null) { // check if the square clicked on would be a valid move
                for (move in validMoves!!) {
                    if (move.contentEquals(coords)) {
                        validMove = true
                    }
                }
            }

            if (activeSquare == null && isPieceActiveColor || !coords.contentEquals(activeSquare) && !validMove && isPieceActiveColor) {
                // if there is no active square, or if the selected square is not a valid move
                wipeCanvas(moveHighlightCanvas)
                fillMoves(filterPossibleMoves(coords, mainBoard))
                setActiveSquare(coords)
                validMoves = filterPossibleMoves(activeSquare!!, mainBoard)
            } else {
                if (!activeSquare.contentEquals(coords)) {
                    if (validMoves != null) {
                        for (move in validMoves!!) {
                            if (coords.contentEquals(move)) {
                                move(activeSquare!!, move, mainBoard)
                                drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
                                wipeCanvas(moveHighlightCanvas)
                                flipActiveSide()

                                activeSquare = null
                                validMoves = null
                                turnCount += 1
                                turnStates[turnCount] = getCopyOfBoard(mainBoard)
                            }
                        }
                    }
                }
            }
        }

        pieceCanvas.onMouseMoved = EventHandler {
            wipeCanvas(mouseHighlightCanvas)
            val coords = determineBoardCoords(it.x, it.y)
            highlightSquare(mouseHighlightCanvas, coords, 4)
            //fillSquare(mouseHighlightCanvas, coords, rgb(0, 255, 125, 0.5))
        }
    }

    private fun setActiveSquare(coords: IntArray) {
        activeSquare = coords
        fillSquare(moveHighlightCanvas, coords, rgb(255, 255, 0, 0.5))
    }

    private fun flipActiveSide() {

        mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
        moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1

        activeSide =
        if (activeSide == PieceColor.WHITE) {
            PieceColor.BLACK
        } else {
            PieceColor.WHITE
        }
        drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
    }

    private fun fillMoves(moves: Array<out IntArray>?) {

        if (moves != null) {
            for (move in moves) {
                fillSquare(moveHighlightCanvas, move, rgb(0, 255, 125, 0.5))
            }
        }
        //fillSquare(moveHightlightCanvas, coords, rgb(255, 255, 0, 0.5))

    }

    private fun resizeActions() {
        scaleCanvas(boardCanvas)
        scaleCanvas(pieceCanvas)
        scaleCanvas(mouseHighlightCanvas)
        scaleCanvas(moveHighlightCanvas)
        drawBoardBackground()
        drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
        update()
    }

    private fun determineBoardCoords(rawX: Double, rawY: Double, actual: Boolean = false): IntArray {
        val xActual: Double = rawX - pieceCanvas.width * boardMargin
        val yActual: Double = rawY - pieceCanvas.height * boardMargin

        var xCoord: Int = -1
        var yCoord: Int = -1

        if (!(xActual / squareSize > 8 || yActual / squareSize > 8 || xActual / squareSize < 0 || yActual / squareSize < 0 && !actual)){
            if (activeSide == PieceColor.BLACK) {
                xCoord = (xActual / squareSize).toInt()
                yCoord = (yActual / squareSize).toInt()
            } else {
                xCoord = (xActual / squareSize).toInt()
                yCoord = 7 - (yActual / squareSize).toInt()
            }
        } else {
            xCoord = (xActual / squareSize).toInt()
            yCoord = (yActual / squareSize).toInt()
        }

        return intArrayOf(xCoord, yCoord)
    }

    private fun highlightSquare(canvas: Canvas, coords: IntArray, highlightWidth: Int = 3) {
        if (checkCoords(coords)){
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

    private fun fillSquare(canvas: Canvas, coords: IntArray, highlightColor: Color) {
        if (coords[0] >= 0 && coords[1] >= 0){
            val gCon = canvas.graphicsContext2D
            val origin = canvas.width * boardMargin
            gCon.fill = highlightColor

            gCon.fillRect(origin + (coords[0] * squareSize),
                    origin + (coords[1] * squareSize),
                    squareSize,
                    squareSize)
        }
    }

    private fun wipeCanvas(canvas: Canvas) {
        canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
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

        flipActiveSide()
        drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)

    }

    fun why() {
        mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
        moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1
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
        if (turnCount > 0) {
            turnCount -= 1
            mainBoard = turnStates[turnCount]!!

            flipActiveSide()

            drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
        }

    }

}
