package chess.view

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import tornadofx.*
import tornadofx.Controller
import java.io.File
import java.lang.Thread.sleep

class MainView : View() {
    override val root : AnchorPane by fxml("/MainView.fxml")
    private val boardImageView : ImageView by fxid("boardImageView")

    init {
        currentStage?.minWidth = 900.0
        currentStage?.minHeight = 650.0
        currentStage?.width = 900.0
        currentStage?.width = 650.0
        boardImageView.image = Image("https://pbs.twimg.com/media/FJ4w3tfXwAgHoPi?format=png&name=small")
    }

    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }
}