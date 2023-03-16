package com.dashkovskiy.project.boards

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val author: String,
    val content: String,
)

@Serializable
data class Board(
    val id: Int,
    val date: String,
    val title: String,
    val description: String,
    val images: List<String>,
    val comments: List<Comment>
)
