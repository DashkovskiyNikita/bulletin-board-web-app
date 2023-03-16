package com.dashkovskiy.project.boards

import com.dashkovskiy.project.api.UserStorage
import com.dashkovskiy.project.boarddetails.BoardItemDetails
import io.kvision.core.*
import io.kvision.form.text.text
import io.kvision.html.button
import io.kvision.html.h6
import io.kvision.html.icon
import io.kvision.navigo.NavigateOptions
import io.kvision.panel.VPanel
import io.kvision.panel.gridPanel
import io.kvision.panel.hPanel
import io.kvision.panel.tabPanel
import io.kvision.routing.routing
import io.kvision.state.bind
import io.kvision.state.bindEach
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh
import org.w3c.dom.NavigatorPlugins

class BulletinBoardPanel(
    private val bulletinBoardViewModel: BulletinBoardViewModel,
    private val onBoardClick : (Board) -> Unit
) : VPanel() {
    init {
        width = 100.perc
        height = 100.vh

        hPanel(justify = JustifyContent.END) {

            marginTop = 24.px
            alignItems = AlignItems.BASELINE

            icon(icon = "fa-solid fa-user") {
                color = Color.name(Col.BLACK)
            }

            h6 {
                marginLeft = 10.px
                marginRight = 24.px
            }.bind(UserStorage.userState) { user ->
                if (user.isLoggedIn) {
                    content = user.fullName
                    onClick {
                        routing.navigate("/userBoards")
                    }
                } else {
                    content = "Войти в акканут"
                    onClick {
                        routing.navigate("/login")
                    }
                }
            }
        }

        tabPanel {  }

        hPanel {
            marginTop = 24.px
            paddingLeft = 48.px
            paddingRight = 48.px
            spacing = 10
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.START

            val searchBar = text {
                width = 500.px
                placeholder = "Поиск"
            }

            button(
                text = "",
                icon = "fa-solid fa-magnifying-glass"
            ) {
                onClick {
                    searchBar.getValue()?.let(bulletinBoardViewModel::searchBoards)
                }
                color = Color.name(Col.WHITE)
            }
        }


        gridPanel(
            templateColumns = "repeat(auto-fill, minmax(400px, 1fr))",
            justifyItems = JustifyItems.CENTER,
            init = { width = 100.perc }
        ).bindEach(bulletinBoardViewModel.boardsState) { board ->
            add(
                BulletinBoardItem(board = board) {
                    onBoardClick(board)
                    routing.navigate("/boardDetails")
                }
            )
        }

        hPanel(
            justify = JustifyContent.CENTER,
            spacing = 10
        ) {
            width = 100.perc
            position = Position.ABSOLUTE
            bottom = 0.px
            paddingBottom = 20.px

            button(
                text = "",
                icon = "fa-solid fa-arrow-left"
            ) {
                onClick {
                    bulletinBoardViewModel.previousPage()
                }
            }

            button(
                text = "",
                icon = "fa-solid fa-arrow-right"
            ) {
                onClick {
                    bulletinBoardViewModel.nextPage()
                }
            }
        }
    }
}