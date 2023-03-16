package com.dashkovskiy.project.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val email: String,
    val password: String
)
