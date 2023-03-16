package com.dashkovskiy.project

import com.dashkovskiy.project.api.ApiHelper
import com.dashkovskiy.project.boarddetails.BoardItemDetails
import com.dashkovskiy.project.boarddetails.BoardItemDetailsViewModel
import com.dashkovskiy.project.boards.BulletinBoardPanel
import com.dashkovskiy.project.boards.BulletinBoardViewModel
import com.dashkovskiy.project.login.LoginPanel
import com.dashkovskiy.project.login.LoginViewModel
import com.dashkovskiy.project.register.RegisterViewModel
import com.dashkovskiy.project.register.RegistrationPanel
import com.dashkovskiy.project.userboards.UserBoardsPanel
import com.dashkovskiy.project.userboards.UserBoardsViewModel
import io.kvision.*
import io.kvision.panel.root
import io.kvision.panel.stackPanel
import io.kvision.routing.Routing
import io.kvision.routing.Strategy
import io.kvision.routing.routing
import io.kvision.utils.perc
import io.kvision.utils.vh

import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    private val apiHelper = ApiHelper()
    private val loginViewModel = LoginViewModel(apiHelper = apiHelper)
    private val registerViewModel = RegisterViewModel(apiHelper = apiHelper)
    private val bulletinBoardViewModel = BulletinBoardViewModel(apiHelper = apiHelper)
    private val userBoardsViewModel = UserBoardsViewModel(apiHelper = apiHelper)
    private val boardItemDetailsViewModel = BoardItemDetailsViewModel(apiHelper = apiHelper)

    init {
        Routing.init(useHash = false, strategy = Strategy.ALL)
    }

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
            stackPanel {
                add(
                    panel = RegistrationPanel(registerViewModel = registerViewModel),
                    route = "/registration"
                )
                add(
                    panel = LoginPanel(loginViewModel = loginViewModel),
                    route = "/login"
                )
                add(
                    UserBoardsPanel(userBoardsViewModel = userBoardsViewModel),
                    route = "/userBoards"
                )
                add(
                    panel = BulletinBoardPanel(
                        bulletinBoardViewModel = bulletinBoardViewModel,
                        onBoardClick = boardItemDetailsViewModel::setBoard
                    ),
                    route = "/mainBoard"
                )
                add(
                    panel = BoardItemDetails(boardItemDetailsViewModel = boardItemDetailsViewModel),
                    route = "/boardDetails"
                )
            }
        }
        routing.navigate("/mainBoard")
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapUploadModule,
        FontAwesomeModule,
        CoreModule,
    )
}
