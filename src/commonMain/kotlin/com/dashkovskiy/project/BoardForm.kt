package com.dashkovskiy.project

import io.kvision.types.KFile
import kotlinx.serialization.Serializable

@Serializable
data class BoardForm(
    val title: String = "",
    val description: String = "",
    val images: List<KFile>? = null
)
