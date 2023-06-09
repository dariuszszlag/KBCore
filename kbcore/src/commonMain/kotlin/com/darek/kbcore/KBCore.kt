package com.darek.kbcore

import com.darek.kbcore.feature.balance.Balance
import com.darek.kbcore.feature.draft.Draft
import com.darek.kbcore.feature.post.Post
import com.darek.kbcore.feature.user.User
import com.darek.kbcore.utils.SwiftFlow
import kotlin.native.ObjCName

interface KBCore {

    val userBalanceFlow: SwiftFlow<Balance>
    val userDraftsFlow: SwiftFlow<List<Draft>>
    val userPostsFlow: SwiftFlow<List<Post>>
    val userDataFlow: SwiftFlow<User>

    fun getDataForUser(@ObjCName(swiftName = "password") userPassword: String)

    fun logout()
}
