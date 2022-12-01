package com.dashkovskiy.project

import io.kvision.core.*
import io.kvision.html.h4
import io.kvision.html.image
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.utils.perc
import io.kvision.utils.px

val bulletinBoardItemStyle = Style {
    height = 350.px
    width = 350.px
    background = Background(Color.name(Col.WHITESMOKE))
    borderRadius = 24.px
    marginTop =  20.px
}

class BulletinBoardItem : VPanel() {
    init {
        addCssStyle(bulletinBoardItemStyle)
        onClick {
            routing.navigate("/itemDetails")
        }
        image(src = "https://tourweek.ru/file/image?path=uploads/gallery_media/3tau0ux29rHO_NRuz9gpeCNhwpHqUVmi.jpg&s=b9f0e4f00965a3309153cd1833837ea4") {
            borderRadiusList = listOf(24.px,24.px,0.px,0.px)
            width = 100.perc
            height = 250.px
        }

        h4 {
            content = "Пример объявления"
            padding = 10.px
            textAlign = TextAlign.LEFT
        }
    }
}