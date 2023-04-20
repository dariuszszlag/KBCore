package com.dariusz.kbcore.feature.user

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserService(
    private val client: HttpClient
) {

    suspend fun getUsers(): UsersResponse = client.get(USERS_URL).body()

}

const val USERS_URL = "/users"

@Serializable
data class UsersResponse(val users: List<User>) {

    companion object {
        fun createUserResponse() = Json.encodeToString(UsersResponse(listOfUsers))

        private val listOfUsers = listOf(
            User(1, "First user"),
            User(2, "Second user"),
            User(3, "Third user"),
            User(4, "Fourth user"),
            User(5, "Fifth user"),
            User(6, "Sixth user")
        )

    }
}

@Serializable
data class User(val id: Int, val name: String)