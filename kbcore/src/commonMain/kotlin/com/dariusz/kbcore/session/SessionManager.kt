package com.dariusz.kbcore.session

object SessionManager {

    fun checkUserAndReturnId(password: String) =
        listOfAuthenticatedUsers.find { it.password == password }?.id ?: 0

    private val listOfAuthenticatedUsers = listOf(
        AuthenticatedUser.FIRST_USER,
        AuthenticatedUser.SECOND_USER,
        AuthenticatedUser.THIRD_USER,
        AuthenticatedUser.FOURTH_USER,
        AuthenticatedUser.FIFTH_USER,
        AuthenticatedUser.SIXTH_USER
    )

    private enum class AuthenticatedUser(val id: Int, val password: String) {
        FIRST_USER(1, "first"),
        SECOND_USER(2, "second"),
        THIRD_USER(3, "third"),
        FOURTH_USER(4, "fourth"),
        FIFTH_USER(5, "fifth"),
        SIXTH_USER(6, "sixth")
    }
}

