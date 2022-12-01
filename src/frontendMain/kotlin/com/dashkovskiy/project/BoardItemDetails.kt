package com.dashkovskiy.project

import io.kvision.core.JustifyContent
import io.kvision.html.h4
import io.kvision.panel.SimplePanel
import io.kvision.utils.perc
import io.kvision.utils.vh

object BoardItemDetails : SimplePanel(){
    init {
        width = 100.perc
        height = 100.vh
        justifyContent = JustifyContent.CENTER
        h4 {
          content = "Board item details"
        }
    }
}