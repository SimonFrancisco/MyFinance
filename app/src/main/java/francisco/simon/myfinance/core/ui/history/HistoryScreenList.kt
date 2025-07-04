package francisco.simon.myfinance.core.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import francisco.simon.myfinance.R
import francisco.simon.myfinance.core.mapper.toCurrencySymbol
import francisco.simon.myfinance.core.mapper.toDateWritten
import francisco.simon.myfinance.domain.entity.Transaction
import java.math.BigDecimal
import java.time.LocalDate

/**
 * History screen list
 * Viewmodel state updates only affect SumInfo and downwards
 *
 * @author Simon Francisco
 */
@Composable
fun HistoryScreenList(
    transactions: List<Transaction>,
) {
    val sum = transactions.sumOf {
        it.amount.toBigDecimal()
    }
    Column(Modifier.fillMaxSize()) {
        SumInfo(sum, transactions)
        LazyColumn {
            items(transactions, key = { it.id }) { transaction ->
                HistoryListItem(transaction)
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun SumInfo(
    sum: BigDecimal,
    transactions: List<Transaction>
) {
    InfoItem(
        leadingTextResId = R.string.sum,
        trailingText = "$sum ${transactions.firstOrNull()?.account?.currency?.toCurrencySymbol() ?: " "}",
        onClick = {
        }
    )
}

@Composable
fun EndInfo(
    endDate: LocalDate,
    showEndPicker: MutableState<Boolean>
) {
    InfoItem(
        leadingTextResId = R.string.end,
        trailingText = endDate.toDateWritten(),
        onClick = {
            showEndPicker.value = true
        }
    )
}

@Composable
fun StartInfo(
    startDate: LocalDate,
    showStartPicker: MutableState<Boolean>
) {
    InfoItem(
        leadingTextResId = R.string.start,
        trailingText = startDate.toDateWritten(),
        onClick = {
            showStartPicker.value = true
        }
    )
}