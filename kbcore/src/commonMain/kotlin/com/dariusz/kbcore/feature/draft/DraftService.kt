package com.dariusz.kbcore.feature.draft

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DraftService(
    private val client: HttpClient
) {

    suspend fun getDrafts(): DraftsResponse = client.get(DRAFTS_URL).body()

}

const val DRAFTS_URL = "/drafts"

@Serializable
data class DraftsResponse(val listOfDrafts: List<Draft>) {
    companion object {
        fun createDraftsResponse() = Json.encodeToString(DraftsResponse(listOfDrafts))

        private val listOfDrafts = listOf(
            Draft(1, 1, "First draft for first user"),
            Draft(2, 1, "Second draft for first user"),
            Draft(3, 2, "First draft for second user"),
            Draft(4, 2, "Second draft for second user"),
            Draft(5, 3, "First draft for third user"),
            Draft(6, 3, "Second draft for third user"),
            Draft(7, 3, "Third draft for third user"),
            Draft(8, 4, "First draft for fourth user"),
            Draft(9, 4, "Second draft for fourth user"),
            Draft(10, 5, "First draft for fifth user"),
            Draft(11, 5, "Second draft for fifth user"),
            Draft(12, 5, "Third draft for fifth user"),
        )

    }
}


@Serializable
data class Draft(val id: Int, val userId: Int, val content: String)