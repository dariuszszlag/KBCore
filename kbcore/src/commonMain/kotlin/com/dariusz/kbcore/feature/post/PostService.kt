package com.dariusz.kbcore.feature.post

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PostService(
    private val client: HttpClient
) {

    suspend fun getPosts(): PostsResponse = client.get(POSTS_URL).body()

}

const val POSTS_URL = "/posts"

@Serializable
data class PostsResponse(val listOfPosts: List<Post>) {
    companion object {
        fun createPostsResponse() = Json.encodeToString(PostsResponse(listOfPosts))

        private val listOfPosts = listOf(
            Post(1, 1, "First post for first user"),
            Post(2, 1, "Second post for first user"),
            Post(3, 2, "First post for second user"),
            Post(4, 2, "Second post for second user"),
            Post(5, 3, "First post for third user"),
            Post(6, 3, "Second post for third user"),
            Post(7, 3, "Third post for third user"),
            Post(8, 4, "First post for fourth user"),
            Post(9, 4, "Second post for fourth user"),
            Post(10, 5, "First post for fifth user"),
            Post(11, 5, "Second post for fifth user"),
            Post(12, 5, "Third post for fifth user"),
        )

    }
}

@Serializable
data class Post(val id: Int, val userId: Int, val content: String)