package com.dashkovskiy.project.boards

import com.dashkovskiy.project.AppScope
import com.dashkovskiy.project.api.ApiHelper
import io.kvision.state.ObservableValue
import kotlinx.coroutines.launch

class BulletinBoardViewModel(private val apiHelper: ApiHelper) {

    val boardsState = ObservableValue(emptyList<Board>())
    private var firstPageBoards = emptyList<Board>()
    private var currentPage = 0

    init {
        getBoardsPage(page = currentPage)
    }

    private fun getBoardsPage(page: Int) = AppScope.launch {
        val boards = apiHelper.getBoards(page = page)
        if (page == 0) firstPageBoards = boards
        boardsState.setState(boards)
    }

    fun nextPage() = getBoardsPage(page = ++currentPage)

    fun previousPage() = getBoardsPage(page = --currentPage)

    fun searchBoards(search: String) = AppScope.launch {
        if (search.isEmpty()) {
            currentPage = 0
            boardsState.setState(firstPageBoards)
        } else {
            val searchedBoards = apiHelper.searchBoards(pattern = search)
            boardsState.setState(searchedBoards)
        }
    }

}