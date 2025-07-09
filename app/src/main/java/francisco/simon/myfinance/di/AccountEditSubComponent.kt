package francisco.simon.myfinance.di

import dagger.BindsInstance
import dagger.Subcomponent
import francisco.simon.myfinance.core.ui.ViewModelFactory

@Subcomponent(modules = [AccountEditViewModelModule::class])
interface AccountEditSubComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance accountId: Int
        ): AccountEditSubComponent
    }
}