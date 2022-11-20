package com.dashkovskiy.project

import io.kvision.Application
import io.kvision.CoreModule
import io.kvision.BootstrapModule
import io.kvision.BootstrapCssModule
import io.kvision.FontAwesomeModule
import io.kvision.core.*
import io.kvision.form.formPanel
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.form.text.text
import io.kvision.html.*
import io.kvision.module
import io.kvision.panel.*
import io.kvision.startApplication
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import io.kvision.utils.vh
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(window.asCoroutineDispatcher())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        root("kvapp") {
            simplePanel{
                add(LoginPanel)
                add(RegistrationPanel)
            }
//            vPanel {
//                width = 100.perc
//                height = 100.vh
//                display = Display.FLEX
//                justifyContent = JustifyContent.CENTER
//                alignItems = AlignItems.CENTER
//                formPanel {
//                    width = 300.px
//                    add(
//                        key = RegistrationForm::name,
//                        control = Text(label = "Имя"),
//                        required = true
//                    )
//                    add(
//                        key = RegistrationForm::surname,
//                        control = Text(label = "Фамилия"),
//                        required = true
//                    )
//                    add(
//                        key = RegistrationForm::password,
//                        control = Password(label = "Пароль"),
//                        required = true
//                    )
//                    add(
//                        key = RegistrationForm::password,
//                        control = Password(label = "Повтор пароля"),
//                        required = true
//                    )
//                    add(
//                        child = button(text = "Зарегистрироваться") { width = 100.perc }
//                    )
//                }
//                link(
//                    label = "Уже зарегистрированы?Войти",
//                    url = "/route"
//                ){
//                    marginTop = 10.px
//                }
//            }
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
