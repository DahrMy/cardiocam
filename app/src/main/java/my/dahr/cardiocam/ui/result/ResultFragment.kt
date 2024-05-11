package my.dahr.cardiocam.ui.result

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
import my.dahr.cardiocam.ui.components.seekbar.ProgressItem
import java.util.ArrayList


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = _binding!!

    private lateinit var progressItemList: ArrayList<ProgressItem>
    private lateinit var mProgressItem: ProgressItem

    private val totalSpan = 200F

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        initDataToSeekbar()
        binding.seekBarResultLevel.setOnTouchListener { _, _ -> true }
        lifecycleScope.launch(Dispatchers.Main) {
            for (i in 0 .. 100) {
                binding.seekBarResultLevel.progress = i
                delay(10)
            }
        }
        return binding.root
    }

    private fun initDataToSeekbar() {
        progressItemList = ArrayList<ProgressItem>()

        // red span
        mProgressItem = ProgressItem()
        mProgressItem.progressItemPercentage = ((60 / totalSpan) * 100)
        mProgressItem.color = R.color.measureResultSlowText // TODO: Change color
        progressItemList.add(mProgressItem)

        // blue span
        mProgressItem = ProgressItem()
        mProgressItem.progressItemPercentage = (100 / totalSpan) * 100
        mProgressItem.color = R.color.measureResultNormalText // TODO: Change color
        progressItemList.add(mProgressItem)
        // green span
        mProgressItem = ProgressItem()
        mProgressItem.progressItemPercentage = (200 / totalSpan) * 100
        mProgressItem.color = R.color.measureResultFastText // TODO: Change color
        progressItemList.add(mProgressItem)

        binding.seekBarResultLevel.apply {
            initData(progressItemList)
            invalidate()
        }
    }


}