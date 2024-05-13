package my.dahr.cardiocam.ui.screen.measurement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import my.dahr.cardiocam.data.model.MeasurementRecord
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MeasurementViewModel @Inject constructor() : ViewModel() {

    var measurementRecord: MeasurementRecord? = null
    private val _progressLiveData = MutableLiveData(0)
    private val _isReadyLiveData = MutableLiveData(false)
    private val measurements = mutableListOf<Int>()

    fun addMeasurement(value: Int) {
        measurements.add(value)

        if (progressLiveData.value!! < 100) {
            _progressLiveData.postValue(progressLiveData.value!! + 1)
        } else {
            measurementRecord = MeasurementRecord(
                measurements.average().roundToInt(),
                System.currentTimeMillis()
            )
            // TODO: save to DB here
            _isReadyLiveData.postValue(true)
        }

    }

    val isReadyLiveData: LiveData<Boolean> get() = _isReadyLiveData
    val progressLiveData: LiveData<Int> get() = _progressLiveData

}