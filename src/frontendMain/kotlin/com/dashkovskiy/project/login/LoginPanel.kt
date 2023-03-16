package com.dashkovskiy.project.login

import com.dashkovskiy.project.LoginForm
import io.kvision.core.*
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.button
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.toast.ToastContainer
import io.kvision.toast.ToastContainerPosition
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

class LoginPanel(private val loginViewModel: LoginViewModel) : VPanel() {

    private var loginFormPanel: FormPanel<LoginForm>? = null
    private val toastContainer = ToastContainer(ToastContainerPosition.BOTTOMRIGHT)

    init {

        loginViewModel.loginState.subscribe { loginResult ->
            loginResult?.let {
                if (loginResult) {
                    loginFormPanel?.clearData()
                    routing.navigate("/mainBoard")
                } else
                    showErrorToast()
            }

        }

        addCssStyle(signInUpPanelStyle)

        loginFormPanel = formPanel {

            width = 300.px

            add(
                key = LoginForm::email,
                control = Text(label = "Логин") {
                    placeholder = "Почта"
                },
                required = true
            )

            add(
                key = LoginForm::password,
                control = Text(label = "Пароль") {
                    placeholder = "Пароль"
                },
                required = true
            )

            validatorMessage = {
                "Неверный логин или пароль"
            }

            button(text = "Войти") {
                width = 300.px
                onClick {
                    loginFormPanel?.let { panel ->
                        if (panel.validate())
                            loginViewModel.tryLogin(panel.getData())
                        else
                            showErrorToast()

                    }
                }
            }

            button(text = "Зарегистрироваться") {
                onClick {
                    routing.navigate("/registration")
                }
                width = 100.perc
                marginTop = 10.px
            }
        }
    }

    private fun showErrorToast() {
        toastContainer.showToast(
            message = "Неверный логин или пароль",
            color = BsColor.DANGERBG
        )
    }
}