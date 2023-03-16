package com.dashkovskiy.project.boarddetails

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.api.ApiHelper
import com.dashkovskiy.project.boards.Board
import io.kvision.state.ObservableValue
import kotlinx.coroutines.launch

class BoardItemDetailsViewModel(private val apiHelper: ApiHelper) {

    val boardItemState = ObservableValue<Board?>(null)

    fun setBoard(board: Board) {
        boardItemState.setState(board)
    }

    private fun updateBoardInfo(boardId: Int) = AppScope.launch {
        val updatedBoard = apiHelper.getBoardById(boardId)
        updatedBoard?.let {
            boardItemState.setState(updatedBoard)
        }
    }

    fun sendComment(comment: String) = AppScope.launch {
        boardItemState.value?.id?.let { boardId ->
            val commentModel = CommentModel(comment = comment)
            val result = apiHelper.sendComment(boardId, commentModel)
            if (result) updateBoardInfo(boardId)
        }
    }
}