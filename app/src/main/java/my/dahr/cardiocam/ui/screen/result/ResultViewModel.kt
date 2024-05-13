package my.dahr.cardiocam.ui.screen.result

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor() : ViewModel() {

    val partWidthPercentage = 100 / 3F

    fun calculateSeekbarPosition(bpm: Double) = when {
        bpm <= 0 -> 0
        bpm in 0.0..60.0 -> ((bpm / 60) * 33.33).toInt()
        bpm in 60.0..100.0 -> ((33.33 + ((bpm - 60) / 40) * 33.33)).toInt()
        bpm in 100.0..200.0 -> ((66.66 + ((bpm - 100) / 100) * 33.33)).toInt()
        else -> 100
    }

}