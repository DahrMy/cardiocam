package my.dahr.cardiocam.ui.screen.measurement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import my.dahr.cardiocam.data.model.MeasurementRecord
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    val repository: MeasurementRepository
) : ViewModel() {

    private val coroutineContext = Dispatchers.IO + Job()

    var measurementRecord: MeasurementRecord? = null

    private val measurements = mutableListOf<Int>()

    private val _progressLiveData = MutableLiveData(0)
    private val _isReadyLiveData = MutableLiveData(false)

    fun addMeasurement(value: Int) {
        measurements.add(value)

        if (progressLiveData.value!! < 100) {

            _progressLiveData.postValue(progressLiveData.value!! + 1)

        } else {

            measurementRecord = MeasurementRecord(
                measurements.average().roundToInt(),
                System.currentTimeMillis()
            )

            viewModelScope.launch(coroutineContext) {
                repository.insertRecordIntoDb(measurementRecord!!)
            }

            _isReadyLiveData.postValue(true)
        }

    }

    val isReadyLiveData: LiveData<Boolean> get() = _isReadyLiveData
    val progressLiveData: LiveData<Int> get() = _progressLiveData

}