package com.dashkovskiy.project.api

import com.dashkovskiy.project.boarddetails.CommentModel
import com.dashkovskiy.project.boards.Board
import com.dashkovskiy.project.login.LoginModel
import com.dashkovskiy.project.models.LoginResponse
import com.dashkovskiy.project.models.RegisterModel
import com.dashkovskiy.project.userboards.UserBoardModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class ApiHelper {

    private val restClient by lazy {
        HttpClient(Js) {
            install(ContentNegotiation) {
                json()
            }
            install(Auth) {
                bearer {
                    loadTokens { UserStorage.userState.value.bearerTokens }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(ApiUtils.BASE_URL)
            }
        }
    }

    suspend fun loginUser(loginModel: LoginModel): Boolean {
        val response = restClient.post(ApiUtils.LOGIN_ROUTE) {
            setBody(loginModel)
        }
        return response.status.isSuccess().also { success ->
            if (!success) return@also
            val userInfo = response.body<LoginResponse>()
            UserStorage.storeUser(userInfo = userInfo)
        }
    }

    suspend fun registerUser(registerModel: RegisterModel): Boolean {
        val response = restClient.post(ApiUtils.REGISTER_ROUTE) {
            setBody(registerModel)
        }
        return response.status.isSuccess()
    }

    suspend fun getBoards(page: Int): List<Board> {
        val response = restClient.get("${ApiUtils.GET_ALL_BOARDS_ROUTE}/$page")
        return if (response.status.isSuccess())
            response.body()
        else
            emptyList()
    }

    suspend fun getBoardById(boardId: Int): Board? {
        val response = restClient.get("board/$boardId")
        return if (response.status.isSuccess())
            response.body()
        else null
    }

    suspend fun searchBoards(pattern: String): List<Board> {
        val response = restClient.get("/board?pattern=$pattern")
        return if (response.status.isSuccess()) {
            response.body()
        } else
            emptyList()
    }

    suspend fun getUserBoards(): List<Board> {
        val response = restClient.get("user/boards")
        return if (response.status.isSuccess())
            response.body()
        else
            emptyList()
    }

    suspend fun deleteUserBoard(id: Int): Boolean {
        val response = restClient.delete("user/board/$id")
        return response.status.isSuccess()
    }

    suspend fun newUserBoard(
        userBoardModel: UserBoardModel,
        images: List<ByteArray>
    ): Boolean {
        val response = restClient.submitFormWithBinaryData(
            url = "user/board",
            formData = formData {
                append("title", userBoardModel.title)
                append("description", userBoardModel.description)
                images.forEachIndexed { index, bytes ->
                    append(
                        key = "image",
                        value = bytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(HttpHeaders.ContentDisposition, "$index.jpeg")
                        }
                    )
                }
            }
        )
        return response.status.isSuccess()
    }

    suspend fun updateUserBoard(userBoardModel: UserBoardModel): Boolean {
        val response = restClient.put(ApiUtils.USER_BOARD_ROUTE) {
            setBody(userBoardModel)
        }
        return response.status.isSuccess()
    }

    suspend fun sendComment(boardId: Int, commentModel: CommentModel): Boolean {
        val response = restClient.post("board/$boardId/newComment") {
            setBody(commentModel)
        }
        return response.status.isSuccess()
    }

}