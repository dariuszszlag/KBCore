package com.dariusz.kbcore.feature.balance

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BalanceService(
    private val client: HttpClient
) {

    suspend fun getBalances(): BalanceResponse = client.get(BALANCE_URL).body()

}

const val BALANCE_URL = "/balances"

@Serializable
data class BalanceResponse(val balanceList: List<Balance>) {
    companion object {
        fun createBalanceResponse() = Json.encodeToString(BalanceResponse(listOfBalances))

        private val listOfBalances = listOf(
            Balance(1, 10, "$"),
            Balance(2, 550, "$"),
            Balance(3, 9921, "$"),
            Balance(4, 52323, "$"),
            Balance(5, 10322, "$"),
        )

    }
}

@Serializable
data class Balance(val userId: Int, val balance: Int, val currency: String) {
    val fullBalance = "$currency$balance"
}