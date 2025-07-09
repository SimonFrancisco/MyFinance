package francisco.simon.myfinance

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import francisco.simon.myfinance.di.ApplicationComponent
import francisco.simon.myfinance.di.DaggerApplicationComponent


class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as App).component
}