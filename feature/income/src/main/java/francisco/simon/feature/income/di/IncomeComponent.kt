package francisco.simon.feature.income.di

import dagger.Component
import francisco.simon.feature.income.ViewModelFactory

@IncomeScope
@Component(modules = [IncomeViewModelModule::class],
    dependencies = [IncomeDependencies::class])
internal interface IncomeComponent {
    fun getViewModelFactory(): ViewModelFactory

}