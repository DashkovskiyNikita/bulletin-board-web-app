package com.dashkovskiy.project

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationForm(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)

