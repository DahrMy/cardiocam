package my.dahr.cardiocam.ui.screen.measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentMeasurementBinding
import my.dahr.cardiocam.hrometer.HeartRateOmeter
import my.dahr.cardiocam.kalmanrx.jama.Matrix
import my.dahr.cardiocam.kalmanrx.jkalman.JKalman
import my.dahr.cardiocam.ui.screen.result.ResultFragment

@AndroidEntryPoint
class MeasurementFragment : Fragment() {

    private var _binding: FragmentMeasurementBinding? = null
    private val binding: FragmentMeasurementBinding get() = _binding!!

    private val viewModel by viewModels<MeasurementViewModel>()

    private var subscription: CompositeDisposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeasurementBinding.inflate(inflater, container, false)

        setListeners()
        initObservers()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dispose()
        subscription = CompositeDisposable()
        startMeasurement()
    }

    override fun onPause() {
        dispose()
        super.onPause()
    }

    private fun dispose() {
        if (subscription?.isDisposed == false) {
            subscription?.dispose()
        }
    }

    private fun setListeners() {
        binding.ivButtonClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initObservers() {
        viewModel.isReadyLiveData.observe(viewLifecycleOwner) { isReady ->
            if (isReady) {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragment_container_view,
                        ResultFragment.newInstance(viewModel.measurementRecord!!)
                    ).commit()
            }
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) { progress ->
            binding.progressBar.progress = progress
            binding.tvPercentage.text = resources.getString(R.string.tv_percentage_text, progress)
        }
    }

    private fun startMeasurement() {
        val kalman = JKalman(2, 1)

        // measurement [x]
        val m = Matrix(1, 1)

        // transitions for x, dx
        val tr = arrayOf(doubleArrayOf(1.0, 0.0), doubleArrayOf(0.0, 1.0))
        kalman.transition_matrix = Matrix(tr)

        // 1s somewhere?
        kalman.error_cov_post = kalman.error_cov_post.identity()


        val bpmUpdates = HeartRateOmeter()
            .withAverageAfterSeconds(3)
            .setFingerDetectionListener(this::onFingerChange)
            .bpmUpdates(binding.surfaceViewCamera)
            .subscribe({

                if (it.value == 0)
                    return@subscribe

                m.set(0, 0, it.value.toDouble())
                kalman.Predict()
                // corrected state [x, dx]
                val c = kalman.Correct(m)

                val bpm = it.copy(value = c.get(0, 0).toInt())
                onBpm(bpm)

            }, Throwable::printStackTrace)

        subscription?.add(bpmUpdates)
    }

    private fun onBpm(bpm: HeartRateOmeter.Bpm) {
        binding.tvBpm.text = "${bpm.value}"
        viewModel.addMeasurement(bpm.value)
    }

    private fun onFingerChange(fingerDetected: Boolean) {
        binding.apply {
            if (fingerDetected) {
                tvMeasurementStatus.text = resources.getString(R.string.tv_measurementStatus_detected)
                tvMeasurementGuide.text = resources.getString(R.string.tv_measurementGuide_detected)
                ivGuide.visibility = View.GONE
                groupProgressBar.visibility = View.VISIBLE
            } else {
                tvMeasurementStatus.text = resources.getString(R.string.tv_measurementStatus_notDetected)
                tvMeasurementGuide.text = resources.getString(R.string.tv_measurementGuide_notDetected)
                ivGuide.visibility = View.VISIBLE
                groupProgressBar.visibility = View.GONE
            }
        }
    }

    private fun animateHeart() {
        // TODO
    }

}