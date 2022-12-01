package com.dashkovskiy.project

import io.kvision.core.onEvent
import io.kvision.form.formPanel
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.html.button
import io.kvision.html.link
import io.kvision.navigo.NavigateOptions
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.utils.perc
import io.kvision.utils.px

object RegistrationPanel : VPanel() {
    init {
        addCssStyle(signInUpPanelStyle)
        formPanel {
            width = 300.px
            add(
                key = RegistrationForm::name,
                control = Text(label = "Имя"),
                required = true
            )
            add(
                key = RegistrationForm::surname,
                control = Text(label = "Фамилия"),
                required = true
            )
            add(
                key = RegistrationForm::email,
                control = Text(label = "Почта"),
                required = true
            )
            add(
                key = RegistrationForm::password,
                control = Password(label = "Пароль"),
                required = true
            )
            add(
                key = RegistrationForm::repeatPassword,
                control = Password(label = "Повтор пароля"),
                required = true
            )
            add(
                child = button(text = "Зарегистрироваться") { width = 100.perc }
            )
            button(text = "Войти"){
                onClick { routing.navigate("/login") }
                width = 100.perc
                marginTop = 10.px
            }
        }
    }
}