package francisco.simon.feature.settings.ui.screens.sync

sealed class SyncScreenState {
    data object Loading : SyncScreenState()
    data class Success(val time: String) : SyncScreenState()
    data object Empty : SyncScreenState()
}