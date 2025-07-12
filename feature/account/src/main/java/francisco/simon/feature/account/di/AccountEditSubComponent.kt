package francisco.simon.feature.account.di

import dagger.BindsInstance
import dagger.Subcomponent
import francisco.simon.feature.account.ViewModelFactory

@Subcomponent(modules = [EditAccountViewModelModule::class])
internal interface AccountEditSubComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @[BindsInstance AccountIdQualifier] accountId: Int
        ): AccountEditSubComponent
    }
}