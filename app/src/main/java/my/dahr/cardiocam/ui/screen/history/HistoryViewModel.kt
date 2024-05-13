package my.dahr.cardiocam.ui.screen.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dahr.cardiocam.data.model.MeasurementRecord
import my.dahr.cardiocam.utils.toModels
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    val repository: HistoryRepository
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + Job()

    private val _recordingsLiveData = MutableLiveData<List<MeasurementRecord>>()

    fun clearHistory() {
        viewModelScope.launch(coroutineContext) {
            repository.clearAllRecordings()
        }
    }

    fun loadRecordingsList() {
        viewModelScope.launch(coroutineContext) {
            repository.getAllRecordingsFlow().collect { entities ->
                _recordingsLiveData.postValue(entities.toModels())
            }
        }
    }

    val recordingsLiveData: LiveData<List<MeasurementRecord>> get() = _recordingsLiveData

}