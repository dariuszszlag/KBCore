package com.darek.kbcore

import com.darek.kbcore.feature.balance.Balance
import com.darek.kbcore.feature.draft.Draft
import com.darek.kbcore.feature.post.Post
import com.darek.kbcore.feature.user.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface KBCore {

    val userBalanceFlow: Flow<Balance>
    val userDraftsFlow: Flow<List<Draft>>
    val userPostsFlow: Flow<List<Post>>
    val userDataFlow: Flow<User>

    fun getDataForUser(userPassword: String): Job
    fun logout()
}
