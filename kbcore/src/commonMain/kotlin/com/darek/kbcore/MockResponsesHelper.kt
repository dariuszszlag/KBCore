package com.darek.kbcore

import com.darek.kbcore.feature.balance.BALANCE_URL
import com.darek.kbcore.feature.balance.BalanceResponse.Companion.createBalanceResponse
import com.darek.kbcore.feature.draft.DRAFTS_URL
import com.darek.kbcore.feature.draft.DraftsResponse.Companion.createDraftsResponse
import com.darek.kbcore.feature.post.POSTS_URL
import com.darek.kbcore.feature.post.PostsResponse.Companion.createPostsResponse
import com.darek.kbcore.feature.user.USERS_URL
import com.darek.kbcore.feature.user.UsersResponse.Companion.createUserResponse
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

internal object MockResponsesHelper {

    private val responseHeaders =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    fun HttpClientConfig<MockEngineConfig>.interceptWithMockedResponses() {
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    BALANCE_URL -> {
                        respond(createBalanceResponse(), HttpStatusCode.OK, responseHeaders)
                    }

                    DRAFTS_URL -> {
                        respond(createDraftsResponse(), HttpStatusCode.OK, responseHeaders)
                    }

                    POSTS_URL -> {
                        respond(createPostsResponse(), HttpStatusCode.OK, responseHeaders)
                    }

                    USERS_URL -> {
                        respond(createUserResponse(), HttpStatusCode.OK, responseHeaders)
                    }

                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
        }
    }

}