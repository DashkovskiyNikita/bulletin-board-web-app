package com.dashkovskiy.project.boarddetails

import com.dashkovskiy.project.api.UserStorage
import io.kvision.core.*
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.panel.*
import io.kvision.state.bind
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh
import io.kvision.require

val boardItemDetailsStyle = Style {
    width = 100.perc
    height = 100.vh
}

class CommentItem(author: String, comment: String) : VPanel() {

    init {
        width = 600.px
        background = Background(color = Color.name(Col.WHITESMOKE))
        borderRadius = 10.px
        padding = 10.px

        h6(content = author)

        p(content = comment)
    }

}

class BoardItemDetails(
    private val boardItemDetailsViewModel: BoardItemDetailsViewModel
) : VPanel() {

    private var slideLeftButton: Div? = null
    private var slideRightButton: Div? = null

    init {

        addCssStyle(boardItemDetailsStyle)

        simplePanel {
            width = 100.perc
            height = 400.px
            background = Background(Color.name(Col.BLACK))
            position = Position.RELATIVE

            onEvent {
                mouseover = {
                    slideLeftButton?.show()
                    slideRightButton?.show()
                }
                mouseleave = {
                    slideLeftButton?.hide()
                    slideRightButton?.hide()
                }
            }

            slideLeftButton = div {
                width = 100.px
                height = 100.perc
                position = Position.ABSOLUTE
                left = 0.px
                opacity = 0.7
                background = Background(color = Color.name(Col.BLACK))
                display = Display.FLEX
                justifyContent = JustifyContent.CENTER
                alignItems = AlignItems.CENTER
                hide()
                icon(
                    icon = "fa-solid fa-chevron-left"
                ) {
                    bottom = 50.perc
                    color = Color.name(Col.WHITE)
                }
            }

            slideRightButton = div {
                width = 100.px
                height = 100.perc
                position = Position.ABSOLUTE
                right = 0.px
                opacity = 0.7
                background = Background(color = Color.name(Col.BLACK))
                display = Display.FLEX
                justifyContent = JustifyContent.CENTER
                alignItems = AlignItems.CENTER
                hide()
                this.icon(
                    icon = "fa-solid fa-chevron-right"
                ) {
                    color = Color.name(Col.WHITE)
                }
            }

            //"https://tourweek.ru/file/image?path=uploads/gallery_media/3tau0ux29rHO_NRuz9gpeCNhwpHqUVmi.jpg&s=b9f0e4f00965a3309153cd1833837ea4"
            hPanel(justify = JustifyContent.CENTER) {
                width = 100.perc
                height = 100.perc
                image(require(".//img/image_ph.jpg")).bind(boardItemDetailsViewModel.boardItemState) { state ->
                    console.log(state)
                }
            }

        }


        h4 {
            marginTop = 24.px
            paddingLeft = 24.px
            paddingRight = 24.px
            width = 100.perc
            textAlign = TextAlign.LEFT
        }.bind(boardItemDetailsViewModel.boardItemState) {
            content = it?.title
        }

        p {
            marginTop = 24.px
            paddingLeft = 24.px
            paddingRight = 24.px
            width = 100.perc
            textAlign = TextAlign.LEFT
        }.bind(boardItemDetailsViewModel.boardItemState) {
            content = it?.description
        }

        h5 {
            marginTop = 24.px
            paddingLeft = 24.px
            paddingRight = 24.px
            content = "Комментарии :"
        }

        //Comment textField with submit button
        hPanel {
            marginTop = 10.px
            paddingLeft = 24.px
            paddingRight = 24.px
            spacing = 10
            alignItems = AlignItems.START
            width = 100.perc

            val commentTextField = text {
                width = 600.px
                placeholder = "Комментарий"
            }

            button(
                text = "",
                icon = "fa-regular fa-comment",
            ) {
                color = Color.name(Col.WHITE)
                onClick {
                    commentTextField.getValue()?.let(boardItemDetailsViewModel::sendComment)
                }
            }.bind(UserStorage.userState) {
                disabled = !it.isLoggedIn
            }
        }

        //Comments
        vPanel {
            marginTop = 10.px
            paddingLeft = 24.px
            paddingRight = 24.px
            spacing = 10
        }.bind(boardItemDetailsViewModel.boardItemState) { boardState ->
            boardState?.let {
                boardState.comments.forEach { comment ->
                    add(
                        child = CommentItem(
                            author = comment.author,
                            comment = comment.content
                        )
                    )
                }
            }
        }
    }
}