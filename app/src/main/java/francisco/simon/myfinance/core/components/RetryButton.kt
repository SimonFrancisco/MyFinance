package francisco.simon.myfinance.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import francisco.simon.myfinance.R
import androidx.compose.ui.unit.dp

@Composable
fun RetryButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onClick) {
            Text(
                text = stringResource(R.string.try_again), style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}