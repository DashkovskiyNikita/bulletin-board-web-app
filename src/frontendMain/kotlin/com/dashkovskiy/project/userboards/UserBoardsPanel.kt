package com.dashkovskiy.project.userboards

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.BoardForm
import com.dashkovskiy.project.boards.Board
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.form.upload.Upload
import io.kvision.html.Button
import io.kvision.html.button
import io.kvision.html.h6
import io.kvision.html.icon
import io.kvision.panel.*
import io.kvision.panel.Direction
import io.kvision.state.bind
import io.kvision.types.base64Encoded
import io.kvision.utils.getDataWithFileContent
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh
import kotlinx.coroutines.launch

val userBoardsStyle = Style {
    height = 100.vh
    width = 100.perc
}

class UserBoardItem(
    private val board: Board,
    val onDeleteClick: () -> Unit,
    val onBoardClick: () -> Unit
) : HPanel() {

    init {
        width = 100.perc
        borderRadius = 10.px
        background = Background(color = Color.name(Col.WHITESMOKE))
        padding = 10.px
        display = Display.FLEX
        justifyContent = JustifyContent.SPACEBETWEEN
        alignItems = AlignItems.CENTER

        onClick {
            onBoardClick()
        }

        h6 {
            textAlign = TextAlign.CENTER
            content = board.title
        }

        icon(icon = "fa-solid fa-trash") {
            onClick {
                onDeleteClick()
            }
        }
    }

}

class UserBoardsPanel(
    private val userBoardsViewModel: UserBoardsViewModel
) : SplitPanel(Direction.VERTICAL) {

    private var editBoardPanel: FormPanel<BoardForm>? = null
    private var updateBoardButton: Button? = null
    private var addNewBoardButton: Button? = null

    init {
        addCssStyle(userBoardsStyle)

        vPanel {

            spacing = 15
            padding = 24.px

            button(
                text = "Новое объявление",
                icon = "fa-solid fa-plus"
            ) {
                width = 100.perc
                onClick {
                    editBoardPanel?.clearData()
                    editBoardPanel?.show()
                    updateBoardButton?.hide()
                    addNewBoardButton?.show()
                }
            }

            button(
                text = "Выйти из аккаунта"
            ) {
                width = 100.perc
                onClick {
                    userBoardsViewModel.logout()
                }
            }

            vPanel().bind(userBoardsViewModel.state) { state ->
                width = 100.perc
                spacing = 10
                alignItems = AlignItems.START
                state.userBoards.forEach { board ->
                    add(
                        UserBoardItem(
                            board = board,
                            onBoardClick = {
                                editBoardPanel?.clearData()
                                editBoardPanel?.setData(BoardForm(title = board.title, description = board.description))
                                editBoardPanel?.show()
                                addNewBoardButton?.hide()
                                updateBoardButton?.show()
                            },
                            onDeleteClick = {
                                userBoardsViewModel.deleteBoard(id = board.id)
                            }
                        )
                    )
                }
            }
        }

        editBoardPanel = formPanel {
            padding = 24.px
            add(
                key = BoardForm::title,
                control = Text(label = "Заголовок"),
                required = true,
                requiredMessage = "Заголовок не может быть пустым",
                validator = { it.getValue()?.isNotEmpty() }
            )
            add(
                key = BoardForm::description,
                control = Text(label = "Описание"),
                required = true,
                requiredMessage = "Описание не может быть пустым",
                validator = { it.getValue()?.isNotEmpty() }
            )
            add(
                key = BoardForm::images,
                control = Upload(multiple = true) {
                    label = "Добавить фото"
                    showUpload = false
                    showCancel = false
                    explorerTheme = true
                    dropZoneEnabled = false
                    allowedFileTypes = setOf("image")
                }
            )
            updateBoardButton = button(text = "Обновить запись") {
                hide()
                width = 100.perc
                onClick {
                    editBoardPanel?.let {
                        if (it.validate()) {
                            userBoardsViewModel.updateBoard(it.getData())
                        }
                    }
                }
            }
            addNewBoardButton = button(text = "Добавить запись") {
                width = 100.perc
                onClick {
                    editBoardPanel?.let { panel ->
                        val result = panel.validate()
                        if (result) {
                            AppScope.launch {
                                userBoardsViewModel.newBoard(panel.getDataWithFileContent())
                            }
                        }
                    }
                }
            }
        }
    }

}