package my.dahr.cardiocam.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import my.dahr.cardiocam.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
/** Start from Android 12 we don't need to create custom splash screens.
* We should use SplashScreen Compat library, but this library doesn't provide a way to add
* Horizontal progress indicator that I should implement according to design.
* See: https://developer.android.com/develop/ui/views/launch/splash-screen#kts
 **/
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding: FragmentSplashScreenBinding get() = _binding!!
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        binding.progressBar.progress = 58


        return binding.root
    }

}