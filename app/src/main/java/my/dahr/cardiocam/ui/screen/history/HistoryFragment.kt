package my.dahr.cardiocam.ui.screen.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import my.dahr.cardiocam.R
import my.dahr.cardiocam.databinding.FragmentHistoryBinding

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding get() = _binding!!

    private val viewModel by viewModels<HistoryViewModel>()

    private val rvAdapter by lazy { HistoryRvAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setListeners()
        setContent()
        initObservers()
        viewModel.loadRecordingsList()

        return binding.root
    }

    private fun setListeners() {
        binding.btClearHistory.setOnClickListener { showDialog() }
    }

    private fun setContent() {
        setToolbar()
        binding.recyclerView.adapter = rvAdapter
    }

    private fun initObservers() {
        viewModel.recordingsLiveData.observe(viewLifecycleOwner) { list ->
            rvAdapter.updateList(list)
        }
    }

    private fun setToolbar() {
        val navIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null).also {
            it?.setTint(resources.getColor(R.color.white, null))
        }
        binding.toolbar.setNavigationIcon(navIcon)
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.dialog_clearHistory_title))
            .setMessage(resources.getString(R.string.dialog_clearHistory_message))
            .setNegativeButton(resources.getString(R.string.dialog_clearHistory_action_negative)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.dialog_clearHistory_action_positive)) { _, _ ->
                viewModel.clearHistory()
            }
            .show()
    }

}