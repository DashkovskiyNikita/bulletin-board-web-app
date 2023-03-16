package com.dashkovskiy.project.userboards

import kotlinx.serialization.Serializable

@Serializable
data class UserBoardModel(
    val title: String,
    val description: String
)
