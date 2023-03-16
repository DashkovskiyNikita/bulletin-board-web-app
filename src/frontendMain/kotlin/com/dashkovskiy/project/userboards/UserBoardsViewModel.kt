package com.dashkovskiy.project.userboards

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.BoardForm
import com.dashkovskiy.project.api.ApiHelper
import com.dashkovskiy.project.api.UserStorage
import com.dashkovskiy.project.boards.Board
import io.ktor.utils.io.core.*
import io.kvision.routing.routing
import io.kvision.state.ObservableValue
import io.kvision.types.base64Encoded
import kotlinx.coroutines.launch

data class UserBoardsState(
    val userBoards: List<Board> = emptyList()
)

class UserBoardsViewModel(
    private val apiHelper: ApiHelper
) {
    val state = ObservableValue(value = UserBoardsState())

    init {
        getUserBoards()
    }

    private fun getUserBoards() = AppScope.launch {
        val response = apiHelper.getUserBoards()
        state.setState(
            state.value.copy(userBoards = response)
        )
    }

    fun updateBoard(form: BoardForm) = AppScope.launch {
        val userBoardModel = UserBoardModel(
            title = form.title,
            description = form.description
        )
        val isSuccess = apiHelper.updateUserBoard(userBoardModel)
        if (isSuccess) getUserBoards()
    }

    fun newBoard(form: BoardForm) = AppScope.launch {
        val userBoardModel = UserBoardModel(
            title = form.title,
            description = form.description
        )
        val isSuccess = apiHelper.newUserBoard(
            userBoardModel = userBoardModel,
            images = form.images?.mapNotNull {
                it.base64Encoded?.toByteArray()
            }.orEmpty()
        )
        if (isSuccess) getUserBoards()
    }

    fun deleteBoard(id: Int) = AppScope.launch {
        val isSuccess = apiHelper.deleteUserBoard(id = id)
        if (isSuccess) getUserBoards()
    }

    fun logout() = AppScope.launch {
        UserStorage.logout()
        routing.navigate("/login")
    }
}