package chess.view

import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import tornadofx.*
import tornadofx.Controller
import java.io.File
import java.lang.Thread.sleep

class MainView : View() {
    override val root : AnchorPane by fxml("/MainView.fxml")
    private val board : Canvas by fxid("board")

    init {
        currentStage?.minWidth = 900.0
        currentStage?.minHeight = 650.0
        currentStage?.width = 900.0
        currentStage?.width = 650.0
        val boardGContext = board.graphicsContext2D
        boardGContext.fill = Color.BLUEVIOLET
        boardGContext.fillRect(0.0, 0.0, 250.0, 250.0)
    }

    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }
}