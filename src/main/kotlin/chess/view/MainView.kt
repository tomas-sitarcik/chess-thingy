package chess.view

import javafx.beans.value.ObservableValue
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color.*
import tornadofx.*

class MainView : View() {
    override val root : AnchorPane by fxml("/fxml/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val board : Canvas by fxid("boardCanvas")
    private val pieces : Canvas by fxid("pieceCanvas")

    init {

        //currentStage?.isResizable = false
        //currentStage?.minWidth = 800.0
        //currentStage?.minHeight = 639.0
        //currentStage?.width = 800.0
        //currentStage?.height = 639.0

        //boardBackground.height = 500.0
        //boardBackground.width = 500.0

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, _: Number, new: Number ->
            scaleCanvas(board)
            drawBoardBackground()
            scaleCanvas(pieces)

            val gCon = pieces.graphicsContext2D
            gCon.drawImage(Image("file:src/resources/images/pieces/reimu.png"), 0.0, 0.0, new.toDouble()/10 , 512.0)
            pieces.toFront()
        })

        anchor.widthProperty().addListener(ChangeListener{
            _: ObservableValue<out Number>?, _: Number, new: Number ->
            scaleCanvas(board)
            drawBoardBackground()
            scaleCanvas(pieces)

            val gCon = pieces.graphicsContext2D
            gCon.drawImage(Image("file:src/resources/images/pieces/reimu.png"), 0.0, 0.0, new.toDouble()/10, 512.0)
            pieces.toFront()
        })



    }

    private fun scaleCanvas(canvas: Canvas) {

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
        println("width " + board.width)
        println("height " + board.height)
    }

    private fun drawBoardBackground() {

        val gCon = board.graphicsContext2D
        val margin = board.width * 0.05
        val sizeActual = board.width - board.width * 0.1
        val squareSize = sizeActual / 8

        gCon.fill = BLACK
        gCon.fillRect(0.0, 0.0, board.width, board.height)
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