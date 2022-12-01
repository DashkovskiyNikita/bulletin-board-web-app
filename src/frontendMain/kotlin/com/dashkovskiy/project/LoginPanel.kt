package com.dashkovskiy.project

import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.button
import io.kvision.html.link
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh

val signInUpPanelStyle = Style {
    width = 100.perc
    height = 100.vh
    display = Display.FLEX
    justifyContent = JustifyContent.CENTER
    alignItems = AlignItems.CENTER
}

object LoginPanel : VPanel() {
    init {
        addCssStyle(signInUpPanelStyle)
        formPanel {
            width = 300.px
            add(
                key = LoginForm::email,
                control = Text(label = "Login"),
                required = true
            )
            add(
                key = LoginForm::password,
                control = Text(label = "Password"),
                required = true
            )
            add(
                child = button(text = "Войти") { width = 300.px }
            )
            button(text = "Зарегистрироваться") {
                onClick {
                    routing.kvNavigate("/registration")
                }
                width = 100.perc
                marginTop = 10.px
            }
        }
    }
}