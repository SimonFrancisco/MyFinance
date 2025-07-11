package francisco.simon.feature.account.di

import dagger.Component
import francisco.simon.feature.account.ViewModelFactory

@AccountScope
@Component(modules = [AccountViewModelModule::class],
    dependencies = [AccountDependencies::class])
internal interface AccountComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getAccountEditComponentFactory(): AccountEditSubComponent.Factory

}