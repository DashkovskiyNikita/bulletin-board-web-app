package com.dashkovskiy.project

import io.kvision.*
import io.kvision.panel.root
import io.kvision.panel.stackPanel
import io.kvision.routing.Routing
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    init {
        Routing.init()
    }

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
            stackPanel{
                add(RegistrationPanel,"/registration")
                add(LoginPanel,"/login")
                //add(BoardItemDetails,"/itemDetails")
                //add(BulletinBoardPanel,"/mainBoard")
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        FontAwesomeModule,
        CoreModule
    )
}
