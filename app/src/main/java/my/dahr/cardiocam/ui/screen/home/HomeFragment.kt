package my.dahr.cardiocam.ui.screen.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentHomeBinding
import my.dahr.cardiocam.ui.screen.history.HistoryFragment
import my.dahr.cardiocam.ui.screen.measurement.MeasurementFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var requestPermission: ActivityResultLauncher<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                changeFragment(MeasurementFragment())
            } else {
                // Do something if not guaranteed
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setSystemBarsColor()
        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.apply {
            btMeasure.setOnClickListener { btMeasureOnClickListener() }
            layoutBtHistory.setOnClickListener { changeFragment(HistoryFragment()) }
        }
    }

    private fun btMeasureOnClickListener() {
        requestPermission.launch(Manifest.permission.CAMERA)
    }

    private fun changeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack("")
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    private fun setSystemBarsColor() {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.primary, null)
        window.navigationBarColor = resources.getColor(R.color.windowBackgroundSecondary, null)
    }

}