package com.darek.kbcore

import kotlinx.coroutines.CoroutineDispatcher

interface KBCoreBuilder {

    fun coroutineDispatcher(coroutineDispatcher: CoroutineDispatcher): KBCoreBuilder

    fun build(): KBCore

}

expect fun kbCoreBuilder(): KBCoreBuilder