package com.dashkovskiy.project.register

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.RegistrationForm
import com.dashkovskiy.project.api.ApiHelper
import com.dashkovskiy.project.models.RegisterModel
import io.kvision.state.ObservableValue
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiHelper: ApiHelper) {

    val registerState = ObservableValue<Boolean?>(null)

    fun tryRegister(form: RegistrationForm) = AppScope.launch {
        val registerModel = RegisterModel(
            name = form.name,
            surname = form.surname,
            email = form.email,
            password = form.password
        )
        val result = apiHelper.registerUser(registerModel)
        registerState.setState(result)
    }
}