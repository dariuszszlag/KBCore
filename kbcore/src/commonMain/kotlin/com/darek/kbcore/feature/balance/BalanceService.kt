package com.darek.kbcore.feature.balance

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class BalanceService(
    private val client: HttpClient
) {

    @NativeCoroutines
    suspend fun getBalances(): BalanceResponse = client.get(BALANCE_URL).body()

}

internal const val BALANCE_URL = "/balances"

@Serializable
internal data class BalanceResponse(val balanceList: List<Balance>) {
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