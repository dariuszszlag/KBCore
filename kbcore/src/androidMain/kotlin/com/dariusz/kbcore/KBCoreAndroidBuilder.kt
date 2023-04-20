package com.dariusz.kbcore

import com.dariusz.kbcore.MockResponsesHelper.interceptWithMockedResponses
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json

class KBCoreAndroidBuilder: KBCoreBuilder {

    private lateinit var coroutineScope: CoroutineScope

    private lateinit var coroutineDispatcherImpl: CoroutineDispatcher

    private lateinit var httpClient: HttpClient

    override fun coroutineDispatcher(coroutineDispatcher: CoroutineDispatcher): KBCoreBuilder {
        coroutineDispatcherImpl = coroutineDispatcher
        return this
    }

    override fun build(): KBCore {
        coroutineScope = CoroutineScope(coroutineDispatcherImpl + SupervisorJob() + CoroutineExceptionHandler { _, _ -> })
        httpClient = HttpClient(MockEngine) {
            interceptWithMockedResponses()
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        return if (::coroutineDispatcherImpl.isInitialized) KBCoreImpl(httpClient, coroutineScope) else throw IllegalArgumentException("No coroutine dispatcher provided")
    }

}

actual fun kbCoreBuilder(): KBCoreBuilder = KBCoreAndroidBuilder()