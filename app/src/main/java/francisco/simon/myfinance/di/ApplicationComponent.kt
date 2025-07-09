package francisco.simon.myfinance.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import francisco.simon.myfinance.core.ui.ViewModelFactory

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getAccountEditComponentFactory(): AccountEditSubComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}