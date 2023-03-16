package com.dashkovskiy.project.boards

import io.kvision.core.*
import io.kvision.html.h4
import io.kvision.html.image
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.require

val bulletinBoardItemStyle = Style {
    height = 350.px
    width = 350.px
    background = Background(Color.name(Col.WHITESMOKE))
    borderRadius = 24.px
    marginTop = 20.px
}

class BulletinBoardItem(
    val board: Board,
    val onItemClick: () -> Unit
) : VPanel() {
    init {
        addCssStyle(bulletinBoardItemStyle)
        onClick {
            onItemClick()
            routing.navigate("/itemDetails")
        }
        image(src = (board.images.firstOrNull() ?: require(".//img/image_ph.jpg")) as? String) {
            borderRadiusList = listOf(24.px, 24.px, 0.px, 0.px)
            width = 100.perc
            height = 250.px
        }

        h4 {
            content = board.title
            padding = 10.px
            textAlign = TextAlign.LEFT
        }
    }
}