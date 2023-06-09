package com.darek.kbcore

import com.darek.kbcore.MockResponsesHelper.interceptWithMockedResponses
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class KBCoreAndroidBuilder: KBCoreBuilder {

    override fun build(): KBCore {
        val httpClient = HttpClient(MockEngine) {
            interceptWithMockedResponses()
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        return KBCoreImpl(httpClient)
    }

}

actual fun kbCoreBuilder(): KBCoreBuilder = KBCoreAndroidBuilder()