package my.dahr.cardiocam.ui.screen.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentResultBinding
import my.dahr.cardiocam.ui.component.seekbar.ProgressPart


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        setContent()

        return binding.root
    }

    private fun setContent() {
        setSeekBarSeparated()
        setSeekBarProgress(50)
    }

    private fun setSeekBarProgress(progress: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            for (i in 0 .. progress) {
                binding.seekBarResultLevel.progress = i
                delay(10)
            }
        }
    }

    private fun setSeekBarSeparated() {
        val partWidthPercentage = 100 / 3F
        val progressPartList = listOf(
            ProgressPart(R.color.seekBarMeasureResultDelayed, partWidthPercentage - 2.5F),
            ProgressPart(R.color.seekBarMeasureResultNormal, partWidthPercentage + 5),
            ProgressPart(R.color.seekBarMeasureResultAccelerated, partWidthPercentage - 2.5F)
        )

        binding.seekBarResultLevel.apply {
            initData(progressPartList)
            invalidate()
        }
    }


}