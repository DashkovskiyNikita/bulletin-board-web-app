package com.dashkovskiy.project

import kotlinx.serialization.Serializable

@Serializable
data class LoginForm(
    val email : String = "",
    val password : String = ""
)

@Serializable
data class UserRequest(
    val name : String,
    val surname : String,
    val email : String,
    val password : String
)