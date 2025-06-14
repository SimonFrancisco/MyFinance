package francisco.simon.myfinance.data

import francisco.simon.myfinance.domain.entity.Account
import francisco.simon.myfinance.domain.entity.Category
import francisco.simon.myfinance.domain.entity.Transaction
import francisco.simon.myfinance.domain.repository.TransactionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor() : TransactionRepository {

    private val transactions = MutableStateFlow<List<Transaction>>(generateTransactions())
        .onStart { delay(SIMULATE_LOADING_DELAY) }

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactions
    }

    private fun generateTransactions() = buildList<Transaction> {
        add(
            Transaction(
                id = 1,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 1,
                    name = "Аренда квартиры",
                    emoji = "\uD83C\uDFE1",
                    isIncome = false
                ),
                amount = "1000",
                comment = ""
            )
        )
        add(
            Transaction(
                id = 2,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 2,
                    name = "Одежда",
                    emoji = "\uD83D\uDC57",
                    isIncome = false
                ),
                amount = "1000",
                comment = ""
            )
        )
        add(
            Transaction(
                id = 3,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 3,
                    name = "На собачку",
                    emoji = "\uD83D\uDC36",
                    isIncome = false
                ),
                amount = "1000",
                comment = "Джек"
            )
        )
        add(
            Transaction(
                id = 4,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 4,
                    name = "На собачку",
                    emoji = "\uD83D\uDC36",
                    isIncome = false
                ),
                amount = "1000",
                comment = "Энии"
            )
        )
        add(
            Transaction(
                id = 5,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 5,
                    name = "Ремонт квартиры",
                    emoji = "РК",
                    isIncome = false
                ),
                amount = "1000",
                comment = ""
            )
        )
        add(
            Transaction(
                id = 6,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 6,
                    name = "Продукты",
                    emoji = "\uD83C\uDF6D",
                    isIncome = false
                ),
                amount = "1000",
                comment = ""
            )

        )
        add(
            Transaction(
                id = 7,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 7,
                    name = "Спортзал",
                    emoji = "\uD83C\uDFCB\u200D♂\uFE0F",
                    isIncome = false
                ),
                amount = "1000",
                comment = ""
            )

        )
        add(
            Transaction(
                id = 8,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 8,
                    name = "Медицина",
                    emoji = "\uD83D\uDC8A",
                    isIncome = false
                ),
                amount = "1000",
                comment = "Francisco"
            )
        )
        add(
            Transaction(
                id = 9,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 9,
                    name = "Зарплата",
                    emoji = "",
                    isIncome = true
                ),
                amount = "1000",
                comment = ""
            )
        )
        add(
            Transaction(
                id = 10,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 10,
                    name = "Подработка",
                    emoji = "",
                    isIncome = true
                ),
                amount = "1000",
                comment = ""
            )
        )
        add(
            Transaction(
                id = 11,
                account = Account(
                    id = 1,
                    name = "Simon",
                    balance = "1000.00",
                    currency = "RUB"
                ),
                category = Category(
                    id = 11,
                    name = "Медицина",
                    emoji = "\uD83D\uDC8A",
                    isIncome = false
                ),
                amount = "1000",
                comment = "Simon"
            )
        )
    }

    companion object {
        private const val SIMULATE_LOADING_DELAY = 2000L
    }
}