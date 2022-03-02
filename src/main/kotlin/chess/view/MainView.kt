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
    private val mainBoard = initBoard()
    private var activeSide = PieceColor.BLACK
    private var activeSquare: IntArray? = null
    private var validMoves: Array<out IntArray>? = null


    init {

        //TODO CLEAN UP THIS FILE

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        drawPieces(mainBoard)
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

            val coords = determineBoardCoords(it.x, it.y)

            if (checkCoords(coords)){
                wipeCanvas(moveHighlightCanvas)
                print(it.x)
                print(" ")
                println(it.y)

                if (validMoves != null) {
                    for (move in validMoves!!) {
                        if (coords.contentEquals(move)) {
                            move(activeSquare!!, move, mainBoard)
                            drawPieces(mainBoard)
                            wipeCanvas(moveHighlightCanvas)
                        }
                    }
                }


                if (!coords.contentEquals(activeSquare)) {
                    setActiveSquare(coords)
                }

                if (activeSquare != null) {
                    validMoves = filterPossibleMoves(activeSquare!!, mainBoard)
                    fillMoves(filterPossibleMoves(activeSquare!!, mainBoard))
                    setActiveSquare(activeSquare!!)


                    if (validMoves != null) {
                        for (move in validMoves!!) {
                            if (coords.contentEquals(move)){
                                move(activeSquare!!, move, mainBoard)
                                drawPieces(mainBoard)
                                wipeCanvas(moveHighlightCanvas)
                            }
                        }
                    }

                } else {
                    fillMoves(filterPossibleMoves(coords, mainBoard))
                    setActiveSquare(coords)
                }
            }


        }

        /*
        pieceCanvas.onDragDetected = EventHandler {
            it.isPrimaryButtonDown
            wipeCanvas(moveHighlightCanvas)
            val coords = determineBoardCoords(it.x, it.y)
            fillMoves(filterPossibleMoves(coords, mainBoard))
            setActiveSquare(coords)
        }

        pieceCanvas.onDragDone = EventHandler {
            move(activeSquare, determineBoardCoords(it.x, it.y), mainBoard)
        }

         */

        pieceCanvas.onMouseMoved = EventHandler {
            wipeCanvas(mouseHighlightCanvas)
            val coords = determineBoardCoords(it.x, it.y)
            highlightSquare(mouseHighlightCanvas, coords, 4)
            //fillSquare(mouseHighlightCanvas, coords, rgb(0, 255, 125, 0.5))
        }

    }

    private fun filterPossibleMoves(coords: IntArray, board: Array<Array<Piece?>>): Array<out IntArray>? {

        var possibleMoves: Array<out IntArray>? = null

        if (checkCoords(coords)) {
            if (getPiece(coords, board) is Piece) {
                var color = getPiece(coords, board)?.color
                possibleMoves = getPossibleMoves(coords, board)
                if (possibleMoves != null) {
                    for (move in possibleMoves) {
                        if (color == getPiece(move, board)?.color) {
                            move[0] = -1
                            move[1] = -1
                        }
                    }
                }
            }
        }

        if (possibleMoves == null) {
            println("fuck")
        }

        return possibleMoves
    }

    private fun setActiveSquare(coords: IntArray) {
        activeSquare = coords
        fillSquare(moveHighlightCanvas, coords, rgb(255, 255, 0, 0.5))
    }

    private fun fillMoves(moves: Array<out IntArray>?) {

        if (moves != null) {
            for (move in moves) {
                fillSquare(moveHighlightCanvas, move, rgb(0, 255, 125, 0.5))
            }
        }
        //fillSquare(moveHightlightCanvas, coords, rgb(255, 255, 0, 0.5))

    }

    private fun checkCoords(coords: IntArray): Boolean {
        return coords[0] in 0..7 && coords[1] in 0..7
    }

    private fun resizeActions() {
        scaleCanvas(boardCanvas)
        scaleCanvas(pieceCanvas)
        scaleCanvas(mouseHighlightCanvas)
        drawBoardBackground()
        drawPieces(mainBoard)
        update()
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
        } else {
            gBoard = board
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

        drawPieces(mainBoard)

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
