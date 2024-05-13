package my.dahr.cardiocam.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dahr.cardiocam.data.model.MeasurementRecord
import my.dahr.cardiocam.utils.toModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: HomeRepository
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + Job()

    private val _lastMeasurementLiveData = MutableLiveData<MeasurementRecord>()
    val lastMeasurementLiveData: LiveData<MeasurementRecord> get() = _lastMeasurementLiveData

    fun loadLastMeasurement() {
        viewModelScope.launch(coroutineContext) {
            _lastMeasurementLiveData.postValue(
                repository.getLastMeasurementRecord().toModel()
            )
        }
    }

}