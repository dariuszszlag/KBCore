package com.darek.kbcore

import com.darek.kbcore.feature.balance.Balance
import com.darek.kbcore.feature.draft.Draft
import com.darek.kbcore.feature.post.Post
import com.darek.kbcore.feature.user.User
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow

interface KBCore {

    @NativeCoroutinesState
    val userBalanceFlow: StateFlow<Balance>
    @NativeCoroutinesState
    val userDraftsFlow: StateFlow<List<Draft>>
    @NativeCoroutinesState
    val userPostsFlow: StateFlow<List<Post>>
    @NativeCoroutinesState
    val userDataFlow: StateFlow<User>

    fun getDataForUser(userPassword: String): Job
    fun logout()
}
