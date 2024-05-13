package my.dahr.cardiocam.ui.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.cardiocam.databinding.FragmentOnboardingPageBinding

private const val HEADLINE = "headline"
private const val DESCRIPTION = "description"
private const val IMAGE_RES = "image"

@AndroidEntryPoint
class OnboardingPageFragment : Fragment() {

    private var _binding: FragmentOnboardingPageBinding? = null
    private val binding: FragmentOnboardingPageBinding get() = _binding!!

    private var headline: String? = null
    private var description: String? = null

    @DrawableRes
    private var imageResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            headline = it.getString(HEADLINE)
            description = it.getString(DESCRIPTION)
            imageResId = it.getInt(IMAGE_RES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingPageBinding.inflate(inflater, container, false)
        setContent()
        return binding.root
    }

    private fun setContent() {
        binding.apply {
            tvHeadline.text = headline
            tvDescription.text = description
            imageResId?.let { id ->
                ivForeground.setImageDrawable(ResourcesCompat.getDrawable(resources, id, null))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(headline: String, description: String, @DrawableRes imageRes: Int) =
            OnboardingPageFragment().apply {
                arguments = Bundle().apply {
                    putString(HEADLINE, headline)
                    putString(DESCRIPTION, description)
                    putInt(IMAGE_RES, imageRes)
                }
            }
    }
}