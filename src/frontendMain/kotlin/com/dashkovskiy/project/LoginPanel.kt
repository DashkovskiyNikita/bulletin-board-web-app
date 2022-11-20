package com.dashkovskiy.project

import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.button
import io.kvision.html.link
import io.kvision.panel.VPanel
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

    //private val loginPanel: FormPanel<RegistrationForm>

    init {
        addCssStyle(signInUpPanelStyle)
        formPanel {
            width = 300.px
            add(
                key = RegistrationForm::name,
                control = Text(label = "Login"),
                required = true
            )
            add(
                key = RegistrationForm::surname,
                control = Text(label = "Password"),
                required = true
            )
            add(
                child = button(text = "Войти") { width = 300.px }
            )
        }
        link(
            label = "Нет аккаунта?Зарегистрироваться",
            url = ""
        ) {
            onEvent {
                click = {
                    this@LoginPanel.hide()
                    RegistrationPanel.show()
                }
            }
            marginTop = 10.px
        }
    }
}