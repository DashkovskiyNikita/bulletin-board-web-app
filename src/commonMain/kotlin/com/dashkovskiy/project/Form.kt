package com.dashkovskiy.project

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationForm(
    val name : String? = null,
    val surname : String? =  null,
    val password : String? = null
)

