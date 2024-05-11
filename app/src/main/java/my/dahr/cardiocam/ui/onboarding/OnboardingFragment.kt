package my.dahr.cardiocam.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding: FragmentOnboardingBinding get() = _binding!!

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        setListeners()
        setContent()
        return binding.root
    }

    private fun setListeners() {
        binding.btNext.setOnClickListener {
            if (viewPager.currentItem < lastItemPosition) {
                viewPager.currentItem++
                setButtonNextText()
            } else {
                Toast.makeText(requireContext(), "End", Toast.LENGTH_SHORT).show()
            }
        }
        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setButtonNextText()
            }
        })
    }

    private fun setContent() {
        setViewPager()
        setButtonNextText()
    }

    private fun setViewPager() {
        val dotsIndicator = binding.dotsIndicator
        viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter(requireContext(), requireActivity())
        dotsIndicator.attachTo(viewPager)
    }

    private fun setButtonNextText() {
        binding.btNext.text = when (viewPager.currentItem) {
            0, lastItemPosition -> resources.getString(R.string.bt_onboarding_text_start)
            else -> resources.getString(R.string.bt_onboarding_text_next)
        }
    }

    private val lastItemPosition get() = ViewPagerAdapter.ITEM_COUNT - 1

}