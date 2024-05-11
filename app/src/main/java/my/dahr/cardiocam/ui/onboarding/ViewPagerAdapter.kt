package my.dahr.cardiocam.ui.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.dahr.cardiocam.R

class ViewPagerAdapter(
    private val context: Context,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    companion object {
        const val ITEM_COUNT = 3
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        context.apply {
            return when (position) {
                0 -> OnboardingPageFragment.newInstance(
                    resources.getString(R.string.onboardingPage_tv_headline_page0),
                    resources.getString(R.string.onboardingPage_tv_description_page0),
                    R.drawable.onboarding_page0
                )

                1 -> OnboardingPageFragment.newInstance(
                    resources.getString(R.string.onboardingPage_tv_headline_page1),
                    resources.getString(R.string.onboardingPage_tv_description_page1),
                    R.drawable.onboarding_page1
                )

                else /* 2 */ -> OnboardingPageFragment.newInstance(
                    resources.getString(R.string.onboardingPage_tv_headline_page2),
                    resources.getString(R.string.onboardingPage_tv_description_page2),
                    R.drawable.onboarding_page2
                )
            }
        }
    }
}