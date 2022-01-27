package chess.view

import javafx.beans.value.ObservableValue
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color.*
import tornadofx.*
import tornadofx.Controller
import java.io.File
import java.lang.Thread.sleep
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class MainView : View() {
    override val root : AnchorPane by fxml("/MainView.fxml")
    private val anchor : AnchorPane by fxid("anchor")
    private val boardBackground : Canvas by fxid("boardBackground")

    init {

        //currentStage?.isResizable = false
        //currentStage?.minWidth = 800.0
        //currentStage?.minHeight = 639.0
        //currentStage?.width = 800.0
        //currentStage?.height = 639.0

        boardBackground.height = 500.0
        boardBackground.width = 500.0

        anchor.heightProperty().addListener(ChangeListener {
            _: ObservableValue<out Number>?, old: Number, new: Number ->
            old as Double
            new as Double


        })

        anchor.widthProperty().addListener(ChangeListener{
            _: ObservableValue<out Number>?, old: Number, new: Number ->
            old as Double
            new as Double
        })

        drawBoard()

    }

    private fun scaleBoardBackground(oldX: Double = -1.0 , newX: Double = -1.0, oldY: Double = -1.0, newY: Double = -1.0) {

        val xRatio: Double = 5.0/8.0
        val yRation: Double = 5.0/6.0



    }

    private fun resizeBoard() {
        if (boardBackground.width > boardBackground.height) {
            boardBackground.height = boardBackground.width
        } else if (boardBackground.width < boardBackground.height) {
            boardBackground.width = boardBackground.height
        }
    }

    fun testA() {
        println("anchor")
        println("width " + anchor.width)
        println("height " + anchor.height)

        println("board")
        println("width " + boardBackground.width)
        println("height " + boardBackground.height)
    }

    private fun drawBoard() {

        val gCon = boardBackground.graphicsContext2D
        val margin = boardBackground.width * 0.05
        val sizeActual = boardBackground.width - boardBackground.width * 0.1
        val squareSize = sizeActual / 8

        gCon.fill = BLACK
        gCon.fillRect(0.0, 0.0, boardBackground.width, boardBackground.height)
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