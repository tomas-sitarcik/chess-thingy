package chess.view

import chess.board.*
import javafx.beans.value.ObservableValue
import javafx.event.EventHandler
import javafx.scene.canvas.Canvas
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*
import javafx.scene.text.Font
import javafx.scene.text.FontSmoothingType
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
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
    private val roundCounter: Label by fxid("roundCounter")
    private val infoField: Text by fxid("infoField")

    private val xRatio: Double = 3.0/16.0
    private val yRatio: Double = 1.0/12.0

    private val boardMargin = 0.030
    private var margin = boardCanvas.width * boardMargin
    private var sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
    private var squareSize = sizeActual / 8
    private var mainBoard = initBoard()
    private var activeColor = PieceColor.WHITE
    private var activeSquare: IntArray? = null
    private var validMoves: Array<out IntArray>? = null

    private var checkStateValidMoves: Array<out IntArray>? = null
    private var checkState: Boolean = false

    private var fillColor: Color = rgb(0, 255, 125, 0.5) // yellowish

    private var turnStates: MutableMap<Int, Array<Array<Piece?>>> = mutableMapOf()
    private var turnCount: Int = 0

    private var magic: Boolean = false // forbidden variable


    init {

        println(javaClass.getResource("resources.images.pieces.black_king.png"))

        //TODO CLEAN UP THIS FILE

        //currentStage?.isResizable = false
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0

        // mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
        // moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1
        // doesn't work here for some reason ¯\_(ツ)_/¯

        drawPieces(mainBoard, pieceCanvas, activeColor, boardMargin, squareSize)
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


        pieceCanvas.onMouseClicked = EventHandler { it ->

            val coords = determineBoardCoords(it.x, it.y)
            checkState = checkForCheck(activeColor, mainBoard)
            infoField.text = checkState.toString()

            if (checkCoords(coords)) {

                var isPieceActiveColor = false
                isPieceActiveColor = getPiece(coords, mainBoard)?.color == activeColor
                var validMove = false
                if (validMoves != null) { // check if the square clicked on would be a valid move
                    for (move in validMoves!!) {
                        if (move.contentEquals(coords)) {
                            validMove = true
                            break
                        }
                    }
                }



                if ((activeSquare == null  || !coords.contentEquals(activeSquare) && !validMove) && isPieceActiveColor) {
                    // if there is no active square, or if the selected square is not a valid move
                    wipeCanvas(moveHighlightCanvas)
                    setActiveSquare(coords)
                    val piece = getPiece(activeSquare!!, mainBoard)

                    validMoves =
                    if (piece?.type == PieceType.KING) {
                        getSafeKingMoves(activeSquare!!, mainBoard)

                    } else {
                        getPossibleMoves(activeSquare!!, mainBoard)
                    }

                    if (checkState && piece?.type != PieceType.KING) { // the king moves are weird so this doesn't apply to them
                        /** makes sure that if the kind is in check, only the moves that will uncheck him are possible **/
                        checkStateValidMoves = getCheckResolvingMoves(activeColor, mainBoard)?.toTypedArray()
                        //fillMoves(checkStateValidMoves)
                        //fillMoves(validMoves, RED)

                        val actuallyValidMoves: ArrayList<IntArray> = arrayListOf()
                        for (move in validMoves!!) {
                            val element = checkStateValidMoves?.find { it.contentEquals(move) }
                            if (element != null) {
                                /** only add the move if it would resolve the check **/
                                if (tryMoveOut(coords, move, activeColor, mainBoard)) {
                                    actuallyValidMoves.add(element)
                                }
                            }
                        }

                        validMoves = actuallyValidMoves.toTypedArray()
                    }

                    fillMoves(validMoves)

                } else {
                    /** not clicking on the active square, and validMoves are not null **/
                    if (!activeSquare.contentEquals(coords) && validMoves != null) {
                        for (move in validMoves!!) {
                            if (coords.contentEquals(move)) {
                                /** TODO EXPLAIN THIS **/
                                if (checkState && getPiece(activeSquare!!, mainBoard)?.type != PieceType.KING) {
                                    if (checkStateValidMoves?.find { it.contentEquals(move) } == null) {
                                        break
                                    }
                                }

                                /** save the board state so it can be reverted to **/
                                turnStates[turnCount] = getCopyOfBoard(mainBoard)
                                turnCount += 1
                                roundCounter.text = "Round $turnCount"

                                /** move, draw pieces, undraw move highlights **/
                                move(activeSquare!!, move, mainBoard)
                                drawPieces(mainBoard, pieceCanvas, activeColor, boardMargin, squareSize)
                                wipeCanvas(moveHighlightCanvas)
                                flipActiveSide()

                                /** make sure these are empty, would lead to weird things happening **/
                                activeSquare = null
                                validMoves = null
                                checkStateValidMoves = null

                                break
                            }
                        }
                    }
                }
            }
        }

        pieceCanvas.onMouseMoved = EventHandler {

            if (!magic) {
                /** very bad and inelegant solution i wish i didn't have to do it like this but this is the only way
                 *  i can change the scaleYs of the canvases, because when i change them in the init block it
                 *  just doesn't work*/
                mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
                moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1
                magic = true
            }

            /** handles the currently hovered over square to be highlighted **/

            wipeCanvas(mouseHighlightCanvas)
            val coords = determineBoardCoords(it.x, it.y)
            if (checkCoords(coords)) {
                highlightSquare(mouseHighlightCanvas, coords, 4)
                //fillSquare(mouseHighlightCanvas, coords, rgb(0, 255, 125, 0.5))

                //fillMoves(getSafeKingMoves(coords, mainBoard), rgb(0, 255, 255, 0.5))
            }
        }

    } //end of init

    private fun setActiveSquare(coords: IntArray) {
        activeSquare = coords
        fillSquare(moveHighlightCanvas, coords, rgb(255, 255, 0, 0.5))
    }

    private fun flipActiveSide() {

        /** flip the highlight layers along the Y axis **/
        mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
        moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1

        /** flip the colors **/
        activeColor =
        if (activeColor == PieceColor.WHITE) {
            PieceColor.BLACK
        } else {
            PieceColor.WHITE
        }

        drawPieces(mainBoard, pieceCanvas, activeColor, boardMargin, squareSize)
    }

    private fun fillMoves(moves: Array<out IntArray>?, color: Color? = null) {

        val usedColor: Color? = color ?: fillColor

        if (moves != null) {
            for (move in moves) {
                fillSquare(moveHighlightCanvas, move, usedColor!!)
            }
        }
        //fillSquare(moveHightlightCanvas, coords, rgb(255, 255, 0, 0.5))

    }

    private fun resizeActions() {

        /** scale canvases, redraw the background, and update all the properties **/

        scaleCanvas(boardCanvas)
        scaleCanvas(pieceCanvas)
        scaleCanvas(mouseHighlightCanvas)
        scaleCanvas(moveHighlightCanvas)
        drawBoardBackground()
        //drawPieces(mainBoard, pieceCanvas, activeSide, boardMargin, squareSize)
        update()
    }

    private fun determineBoardCoords(rawX: Double, rawY: Double, actual: Boolean = false): IntArray {

        /** returns the actual board coordinate from the raw coordinates from the click MouseEvent **/

        val xActual: Double = rawX - pieceCanvas.width * boardMargin
        val yActual: Double = rawY - pieceCanvas.height * boardMargin

        var xCoord: Int = -1
        var yCoord: Int = -1

        if (!(xActual / squareSize > 8 || yActual / squareSize > 8 || xActual / squareSize < 0 || yActual / squareSize < 0 && !actual)){
            if (activeColor == PieceColor.BLACK) {
                xCoord = (xActual / squareSize).toInt()
                yCoord = (yActual / squareSize).toInt()
            } else {
                xCoord = (xActual / squareSize).toInt()
                yCoord = 7 - (yActual / squareSize).toInt()
            }
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
    //end of init

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

        /** scales the passed canvas, making sure to not cause weird behaviour if one of them has a negative scale
         *  this also bypasses the need to redraw the pieces every time **/

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

    private fun update() {
        margin = boardCanvas.width * boardMargin
        sizeActual = boardCanvas.width - boardCanvas.width * boardMargin * 2
        squareSize = sizeActual / 8
    }


    fun testA() {

        //flipActiveSide()
        //mouseHighlightCanvas.scaleY = mouseHighlightCanvas.scaleY * -1
        //moveHighlightCanvas.scaleY = moveHighlightCanvas.scaleY * -1
        drawPieces(mainBoard, pieceCanvas, activeColor, boardMargin, squareSize)

        //fillMoves(getCheckResolvingMoves(PieceColor.WHITE, mainBoard)?.toTypedArray())
        //fillColor = rgb(0, 0, 0, 0.0)
        //fillMoves(getAllMovesForColor(PieceColor.BLACK, mainBoard).toTypedArray())

        //fillMoves(getAllMovesForColor(activeSide, mainBoard).toTypedArray())

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

        val columnLetters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H")
        gCon.fill = BLACK

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

        (1..8).forEachIndexed { i, letter ->
            gCon.fillText(letter.toString(), margin / 2,
                                             squareSize * i + margin + squareSize / 2 + margin * 0.425,
                                                squareSize)
            gCon.fillText(letter.toString(), 2 * margin + 8 * squareSize - margin / 2,
                                             squareSize * i + margin + squareSize / 2 + margin * 0.425,
                                                squareSize)
        }
    }

    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }

    fun revertToRound(){
        if (turnCount > 0) {
            turnStates.remove(turnCount)
            turnCount -= 1
            mainBoard = turnStates[turnCount]!!
            roundCounter.text = "Round $turnCount"

            flipActiveSide()

            drawPieces(mainBoard, pieceCanvas, activeColor, boardMargin, squareSize)
        }

    }

}
