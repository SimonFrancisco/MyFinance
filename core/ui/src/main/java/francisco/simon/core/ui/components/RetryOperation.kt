package francisco.simon.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import francisco.simon.core.ui.R


/**
 * Retry operation when state is Failure,
 * it shows error reason and a retry button!
 * @author Simon Francisco
 */
@Composable
fun RetryCall(
    @StringRes errorRes: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ErrorText(errorRes)
            Spacer(modifier = Modifier.height(4.dp))
            RetryButton(onClick)
        }

    }
}

@Composable
private fun RetryButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors().copy(containerColor = MaterialTheme.colorScheme.onErrorContainer)
    ) {
        Text(
            text = stringResource(R.string.try_again),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun ErrorText(errorRes: Int) {
    Text(
        text = stringResource(errorRes),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}