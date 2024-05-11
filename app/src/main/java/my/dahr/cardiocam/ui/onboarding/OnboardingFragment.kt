package my.dahr.cardiocam.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.dahr.cardiocam.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding: FragmentOnboardingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        val dotsIndicator = binding.dotsIndicator
        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(requireContext(), requireActivity())
        dotsIndicator.attachTo(viewPager)

        return binding.root
    }

}