package com.darek.kbcore

import com.darek.kbcore.feature.balance.Balance
import com.darek.kbcore.feature.balance.BalanceService
import com.darek.kbcore.feature.draft.Draft
import com.darek.kbcore.feature.draft.DraftService
import com.darek.kbcore.feature.post.Post
import com.darek.kbcore.feature.post.PostService
import com.darek.kbcore.feature.user.User
import com.darek.kbcore.feature.user.UserService
import com.darek.kbcore.session.SessionManager.checkUserAndReturnId
import com.darek.kbcore.utils.SwiftFlow
import com.darek.kbcore.utils.mp
import io.ktor.client.HttpClient
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KBCoreImpl(
    client: HttpClient
) : KBCore {

    private val userId = MutableStateFlow(0)

    private val balanceService = BalanceService(client)
    private val draftService = DraftService(client)
    private val postService = PostService(client)
    private val userService = UserService(client)

    private val _userBalanceFlow = MutableStateFlow(Balance(0, 0, "$"))
    private val _userDraftsFlow = MutableStateFlow(listOf<Draft>())
    private val _userPostsFlow = MutableStateFlow(listOf<Post>())
    private val _userDataFlow = MutableStateFlow(User(0, "No such user"))

    override val userBalanceFlow: SwiftFlow<Balance> = _userBalanceFlow.asStateFlow().mp
    override val userDraftsFlow: SwiftFlow<List<Draft>> = _userDraftsFlow.asStateFlow().mp
    override val userPostsFlow: SwiftFlow<List<Post>> = _userPostsFlow.asStateFlow().mp
    override val userDataFlow: SwiftFlow<User> = _userDataFlow.asStateFlow().mp

    override fun getDataForUser(userPassword: String) {
        MainScope().launch {
            userId.value = checkUserAndReturnId(userPassword)
            _userBalanceFlow.value =
                balanceService.getBalances().balanceList.find { it.userId == userId.value }
                    ?: Balance(0, 0, "$")
            _userDraftsFlow.value =
                draftService.getDrafts().listOfDrafts.filter { it.userId == userId.value }
            _userPostsFlow.value =
                postService.getPosts().listOfPosts.filter { it.userId == userId.value }
            _userDataFlow.value =
                userService.getUsers().users.find { it.id == userId.value }
                    ?: User(0, "No such user")
        }
    }

    override fun logout() {
        userId.value = 0
        _userBalanceFlow.value = Balance(0, 0, "$")
        _userDraftsFlow.value = listOf<Draft>()
        _userPostsFlow.value = listOf<Post>()
        _userDataFlow.value = User(0, "No such user")
    }
}
