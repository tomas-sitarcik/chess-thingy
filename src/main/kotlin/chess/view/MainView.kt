package chess.view

import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import tornadofx.*
import tornadofx.Controller
import java.io.File

class MainView : View() {
    override val root : AnchorPane by fxml("/MainView.fxml")
    private val boardImageView : ImageView by fxid("boardImageView")

    init {
        currentStage?.minWidth = 800.0
        currentStage?.minHeight = 600.0
        currentStage?.width = 800.0
        currentStage?.width = 600.0
        //boardImageView.image = Image("https://pbs.twimg.com/media/FJ4w3tfXwAgHoPi?format=png&name=small")
    }

    fun print() {
        println(currentStage?.width)
        println(currentStage?.height)
    }
}