package com.dashkovskiy.project.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val name : String,
    val surname : String,
    val email : String
)
