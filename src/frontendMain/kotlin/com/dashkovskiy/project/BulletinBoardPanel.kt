package com.dashkovskiy.project

import io.kvision.core.JustifyContent
import io.kvision.core.JustifyItems
import io.kvision.html.button
import io.kvision.panel.VPanel
import io.kvision.panel.gridPanel
import io.kvision.panel.hPanel
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh

object BulletinBoardPanel : VPanel() {
    init {
        width = 100.perc
        height = 100.vh
        gridPanel(
            templateColumns = "repeat(auto-fill, minmax(400px, 1fr))",
            justifyItems = JustifyItems.CENTER
        ) {
            width = 100.perc
            repeat(9) {
                add(BulletinBoardItem())
            }
        }
        hPanel(
            justify = JustifyContent.CENTER,
            spacing = 10
        ) {
            marginTop = 20.px
            button(text = "Предыдущая страница")
            button(text = "Следующая страница")
        }
    }
}