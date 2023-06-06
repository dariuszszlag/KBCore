package com.darek.kbcore

import com.darek.kbcore.MockResponsesHelper.interceptWithMockedResponses
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json

internal class KBCoreAndroidBuilder: KBCoreBuilder {

    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineExceptionHandler { _, _ -> })

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
        return KBCoreImpl(httpClient, coroutineScope)
    }

}

actual fun kbCoreBuilder(): KBCoreBuilder = KBCoreAndroidBuilder()