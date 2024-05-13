package my.dahr.cardiocam.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentSplashScreenBinding
import my.dahr.cardiocam.ui.screen.onboarding.OnboardingFragment

@SuppressLint("CustomSplashScreen")
/** Start from Android 12 we don't need to create custom splash screens.
* We should use SplashScreen Compat library, but this library doesn't provide a way to add
* Horizontal progress indicator that I should implement according to design.
* See: https://developer.android.com/develop/ui/views/launch/splash-screen#kts
 **/
@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding: FragmentSplashScreenBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isReady = lifecycleScope.async(Dispatchers.Main) {
            for (i in 0 .. 100) {
                delay(10)
                setPercentage(i)
            }
            return@async true
        }

        lifecycleScope.launch(Dispatchers.Main) {
            if (isReady.await()) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, OnboardingFragment())
                    .commit()
            }

        }

    }

    private fun setPercentage(value: Int) {
        binding.tvPercentage.text =
            String.format(resources.getString(R.string.tv_percentage_text), value)
        binding.progressBar.progress = value
    }

}