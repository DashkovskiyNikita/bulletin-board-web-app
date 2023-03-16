package com.dashkovskiy.project.register

import com.dashkovskiy.project.RegistrationForm
import com.dashkovskiy.project.login.signInUpPanelStyle
import io.kvision.core.BsColor
import io.kvision.form.FormPanel
import io.kvision.form.formPanel
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.panel.VPanel
import io.kvision.routing.routing
import io.kvision.toast.ToastContainer
import io.kvision.toast.ToastContainerPosition
import io.kvision.utils.perc
import io.kvision.utils.px

class RegistrationPanel(private val registerViewModel: RegisterViewModel) : VPanel() {

    private var registrationPanel: FormPanel<RegistrationForm>? = null

    private val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()

    private val toastContainer = ToastContainer(ToastContainerPosition.BOTTOMRIGHT)

    init {

        addCssStyle(signInUpPanelStyle)

        registerViewModel.registerState.subscribe { result ->
            result?.let {
                if (result) {
                    showSuccessToast()
                    registrationPanel?.clearData()
                } else showErrorToast()
            }
        }

        registrationPanel = formPanel {

            width = 300.px

            add(
                key = RegistrationForm::name,
                required = true,
                control = Text(label = "Имя"),
                requiredMessage = "Поле не может быть пустым",
                validator = { it.getValue()?.isNotEmpty() }
            )

            add(
                key = RegistrationForm::surname,
                control = Text(label = "Фамилия"),
                required = true,
                requiredMessage = "Поле не может быть пустым",
                validator = { it.getValue()?.isNotEmpty() }
            )

            add(
                key = RegistrationForm::email,
                control = Text(label = "Почта"),
                required = true,
                requiredMessage = "Не верный шаблон почты",
                validator = {
                    emailRegex.matches(it.getValue().orEmpty())
                }
            )

            add(
                key = RegistrationForm::password,
                control = Password(label = "Пароль"),
                required = true,
                requiredMessage = "Пароль должен содержать не менее 8 символов",
                validator = { (it.getValue()?.length ?: 0) >= 8 }
            )

            add(
                key = RegistrationForm::repeatPassword,
                control = Password(label = "Повтор пароля"),
                required = true,
                requiredMessage = "Пароль должен содержать не менее 8 символов",
                validator = { (it.getValue()?.length ?: 0) >= 8 }
            )

            button(text = "Зарегистрироваться") {
                width = 100.perc
                onClick {
                    registrationPanel?.let { panel ->
                        if (panel.validate()) registerViewModel.tryRegister(panel.getData())
                    }

                }
            }


            button(text = "Войти") {

                width = 100.perc
                marginTop = 10.px

                onClick {
                    routing.navigate("/login")
                }

            }

            validator = {
                it[RegistrationForm::password] == it[RegistrationForm::repeatPassword]
            }

            validatorMessage = {
                "Пароли не совпадают"
            }

        }
    }

    private fun showSuccessToast() {
        toastContainer.showToast(
            message = "Аккаунт успешно создан",
            color = BsColor.SUCCESSBG
        )
    }

    private fun showErrorToast() {
        toastContainer.showToast(
            message = "Что-то пошло не так,повторите попытку",
            color = BsColor.DANGERBG
        )
    }
}