package chess.view

import chess.app.Styles
import tornadofx.*
import tornadofx.Stylesheet.Companion.label

class MainView : View("Hello TornadoFX chat :DDDD") {
    override val root = hbox {
        label(title) {
            addClass(Styles.heading)
        }
    }
}