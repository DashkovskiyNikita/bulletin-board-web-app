package com.dashkovskiy.project.login

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.LoginForm
import com.dashkovskiy.project.api.ApiHelper
import io.kvision.state.ObservableValue
import kotlinx.coroutines.launch

class LoginViewModel(private val apiHelper: ApiHelper) {

    val loginState = ObservableValue<Boolean?>(null)

    fun tryLogin(form: LoginForm) = AppScope.launch {
        val loginModel = LoginModel(
            email = form.email,
            password = form.password
        )
        val result = apiHelper.loginUser(loginModel = loginModel)
        println(result)
        loginState.setState(result)
    }

}