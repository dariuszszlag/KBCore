package com.darek.kbcore

interface KBCoreBuilder {

    fun build(): KBCore

}

expect fun kbCoreBuilder(): KBCoreBuilder