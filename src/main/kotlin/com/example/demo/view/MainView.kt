package com.example.demo.view

import com.example.demo.app.Styles
import tornadofx.*
import tornadofx.Stylesheet.Companion.label

class MainView : View("Hello TornadoFX chat :DDDD") {
    override val root = hbox {
        label(title) {
            addClass(Styles.heading)
        }
    }
}