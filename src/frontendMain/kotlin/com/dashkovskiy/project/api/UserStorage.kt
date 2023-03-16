package com.dashkovskiy.project.api

import com.dashkovskiy.project.LocalStorageKey
import com.dashkovskiy.project.models.LoginResponse
import io.ktor.client.plugins.auth.providers.*
import io.kvision.state.ObservableValue
import kotlinx.browser.localStorage

data class UserState(
    val bearerTokens: BearerTokens? = null,
    val isLoggedIn: Boolean = false,
    val name: String = "",
    val surname: String = ""
) {
    val fullName: String
        get() = "$name $surname"
}

object UserStorage {

    val userState = ObservableValue(UserState())

    init {
        val access = localStorage.getItem(LocalStorageKey.ACCESS_TOKEN)
        val refresh = localStorage.getItem(LocalStorageKey.REFRESH_TOKEN)
        val isLoggedIn = localStorage.getItem(LocalStorageKey.IS_LOGGED_IN)
        val name = localStorage.getItem(LocalStorageKey.USER_NAME)
        val surname = localStorage.getItem(LocalStorageKey.USER_SURNAME)
        userState.setState(
            userState.value.copy(
                bearerTokens = BearerTokens(
                    accessToken = access.orEmpty(),
                    refreshToken = refresh.orEmpty()
                ),
                isLoggedIn = isLoggedIn.toBoolean(),
                name = name.orEmpty(),
                surname = surname.orEmpty()
            )
        )

    }

    fun storeUser(userInfo: LoginResponse) {
        with(localStorage) {
            setItem(LocalStorageKey.ACCESS_TOKEN, userInfo.accessToken)
            setItem(LocalStorageKey.REFRESH_TOKEN, userInfo.refreshToken)
            setItem(LocalStorageKey.IS_LOGGED_IN, true.toString())
            setItem(LocalStorageKey.USER_NAME, userInfo.name)
            setItem(LocalStorageKey.USER_SURNAME, userInfo.surname)
        }
        userState.setState(
            userState.value.copy(
                bearerTokens = BearerTokens(
                    accessToken = userInfo.accessToken,
                    refreshToken = userInfo.refreshToken
                ),
                isLoggedIn = true,
                name = userInfo.name,
                surname = userInfo.surname
            )
        )
    }

    fun logout() {
        userState.setState(
            userState.value.copy(
                bearerTokens = null,
                isLoggedIn = false,
                name = "",
                surname = ""
            )
        )
        localStorage.clear()
    }
}