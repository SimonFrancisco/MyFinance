package francisco.simon.feature.income.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import francisco.simon.feature.income.ui.screens.edit_income.EditIncomeScreenViewModel

@Module
internal interface EditIncomeViewModelModule {
    @[Binds IntoMap ViewModelKey(EditIncomeScreenViewModel::class)]
    fun bindEditIncomeScreenViewModel(viewModel: EditIncomeScreenViewModel): ViewModel
}