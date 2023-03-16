package com.dashkovskiy.project.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
