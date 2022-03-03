package chess.view

import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane

fun scaleCanvas(canvas: Canvas, anchorPane: AnchorPane) {

    val desiredWidth = anchorPane.width - (anchorPane.width * 3.0/16.0 * 2)
    val desiredHeight = anchorPane.height - (anchorPane.height * 1.0/12.0 * 2)

    canvas.scaleX = desiredWidth / canvas.width
    canvas.scaleY = desiredHeight / canvas.height

    if (canvas.scaleX > canvas.scaleY) {
        canvas.scaleX = canvas.scaleY
    } else {
        canvas.scaleY = canvas.scaleX
    }

}