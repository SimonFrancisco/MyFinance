package francisco.simon.feature.settings.ui.screens.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import francisco.simon.feature.settings.domain.synchronization.GetLastSyncTimeUseCase
import francisco.simon.feature.settings.ui.mappers.toReadableTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


internal class SyncScreenViewModel @Inject constructor(
    private val getLastSyncTimeUseCase: GetLastSyncTimeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SyncScreenState> =
        MutableStateFlow(SyncScreenState.Loading)
    val state: StateFlow<SyncScreenState> = _state

    fun loadLastSyncTime() {
        viewModelScope.launch {
            _state.update {
                SyncScreenState.Loading
            }
            val lastSyncTime = getLastSyncTimeUseCase()
            if (lastSyncTime == 0L) {
                _state.update {
                    SyncScreenState.Empty
                }
            } else {
                _state.update {
                    SyncScreenState.Success(lastSyncTime.toReadableTime())
                }
            }
        }

    }
}