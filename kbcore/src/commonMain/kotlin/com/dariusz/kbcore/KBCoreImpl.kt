package com.dariusz.kbcore

import com.dariusz.kbcore.feature.balance.Balance
import com.dariusz.kbcore.feature.balance.BalanceService
import com.dariusz.kbcore.feature.draft.Draft
import com.dariusz.kbcore.feature.draft.DraftService
import com.dariusz.kbcore.feature.post.Post
import com.dariusz.kbcore.feature.post.PostService
import com.dariusz.kbcore.feature.user.User
import com.dariusz.kbcore.feature.user.UserService
import com.dariusz.kbcore.session.SessionManager.checkUserAndReturnId
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KBCoreImpl(
    private val client: HttpClient,
    private val coroutineScope: CoroutineScope
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

    override val userBalanceFlow: Flow<Balance> = _userBalanceFlow.asStateFlow()
    override val userDraftsFlow: Flow<List<Draft>> = _userDraftsFlow.asStateFlow()
    override val userPostsFlow: Flow<List<Post>> = _userPostsFlow.asStateFlow()
    override val userDataFlow: Flow<User> = _userDataFlow.asStateFlow()

    override fun getDataForUser(userPassword: String) = coroutineScope.launch {
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

    override fun logout() {
        userId.value = 0
        _userBalanceFlow.value = Balance(0, 0, "$")
        _userDraftsFlow.value = listOf<Draft>()
        _userPostsFlow.value = listOf<Post>()
        _userDataFlow.value = User(0, "No such user")
    }
}
