package com.dashkovskiy.project

import kotlinx.serialization.Serializable

@Serializable
data class LoginForm(
    val email : String = "",
    val password : String = ""
)