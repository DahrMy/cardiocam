package my.dahr.cardiocam.ui.screen.result

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.dahr.cardiocam.R
import my.dahr.cardiocam.data.model.MeasurementRecord
import my.dahr.cardiocam.databinding.FragmentResultBinding
import my.dahr.cardiocam.ui.component.seekbar.ProgressPart
import my.dahr.cardiocam.ui.screen.history.HistoryFragment
import my.dahr.cardiocam.ui.screen.home.HomeFragment
import my.dahr.cardiocam.utils.timestampToDate
import my.dahr.cardiocam.utils.timestampToTime

const val MEASUREMENT_RECORD = "measurement_record"

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = _binding!!

    private val viewModel by viewModels<ResultViewModel>()

    private lateinit var measurementResult: MeasurementRecord


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            measurementResult = arguments?.getParcelable(MEASUREMENT_RECORD, MeasurementRecord::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            measurementResult = arguments?.getParcelable(MEASUREMENT_RECORD)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        setListeners()
        setContent()

        return binding.root
    }

    private fun setListeners() {
        binding.layoutBtHistory.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("")
                .replace(R.id.fragment_container_view, HistoryFragment())
                .commit()
        }
        binding.btClose.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, HomeFragment())
                .commit()
        }
    }

    private fun setContent() {
        setSeekBar()
        setHeartRateLevel()
        setTime()
    }
    private fun setHeartRateLevel() {
        binding.apply {
            @Suppress("KotlinConstantConditions")
            when {
                measurementResult.bpm < 60 -> {
                    tvResultLevel.text =
                        resources.getString(R.string.tv_resultLevel_text_delayed)
                    tvResultLevel.setTextColor(
                        resources.getColor(R.color.tvMeasureResultDelayedText, null)
                    )
                    tvRangeDelayed.setTextColor(
                        resources.getColor(R.color.tvMeasureResultRangeInclude, null)
                    )
                }

                measurementResult.bpm in 60 .. 100 -> {
                    tvResultLevel.text =
                        resources.getString(R.string.tv_resultLevel_text_normal)
                    tvResultLevel.setTextColor(
                        resources.getColor(R.color.tvMeasureResultNormalText, null)
                    )
                    tvRangeNormal.setTextColor(
                        resources.getColor(R.color.tvMeasureResultRangeInclude, null)
                    )
                }

                measurementResult.bpm > 100 -> {
                    tvResultLevel.text =
                        resources.getString(R.string.tv_resultLevel_text_accelerated)
                    tvResultLevel.setTextColor(
                        resources.getColor(R.color.tvMeasureResultAcceleratedText, null)
                    )
                    tvRangeAccelerated.setTextColor(
                        resources.getColor(R.color.tvMeasureResultRangeInclude, null)
                    )
                }
            }
        }

        setSeekBarProgress(viewModel.calculateSeekbarPosition(measurementResult.bpm.toDouble()))

    }

    private fun setTime() {
        val date = timestampToDate(measurementResult.timestamp)
        val time = timestampToTime(measurementResult.timestamp)

        binding.tvResultTime.text = String.format(
            resources.getString(R.string.tv_resultTime),
            time, date
        )

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setSeekBar() {
        binding.seekBarResultLevel.setOnTouchListener { _, _ -> true }
        setSeekBarSeparated()
    }

    private fun setSeekBarProgress(progress: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            for (i in 0..progress) {
                binding.seekBarResultLevel.progress = i
                delay(10)
            }
        }
    }

    private fun setSeekBarSeparated() {
        val percentage = viewModel.partWidthPercentage
        val progressPartList = listOf(
            ProgressPart(R.color.seekBarMeasureResultDelayed, percentage),
            ProgressPart(R.color.seekBarMeasureResultNormal, percentage),
            ProgressPart(R.color.seekBarMeasureResultAccelerated, percentage)
        )

        binding.seekBarResultLevel.apply {
            initData(progressPartList)
            invalidate()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(measurementRecord: MeasurementRecord) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MEASUREMENT_RECORD, measurementRecord)
                }
            }
    }

}