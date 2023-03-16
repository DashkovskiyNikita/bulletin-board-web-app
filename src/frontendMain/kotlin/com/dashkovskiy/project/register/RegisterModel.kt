package com.dashkovskiy.project.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val name : String,
    val surname : String,
    val email : String,
    val password : String
)
