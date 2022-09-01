package com.example.jobsearch.ui.main.search

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jobsearch.R
import com.example.jobsearch.data.db.entity.JobSearchHistory
import com.example.jobsearch.databinding.FragmentJobSearchBinding
import com.example.jobsearch.ui.common.Injector
import com.example.jobsearch.util.bindText
import com.example.jobsearch.util.log
import com.google.android.flexbox.*
import java.util.*

/**
 * Job search fragment
 */
class JobSearchFragment : Fragment() {

    private var _binding: FragmentJobSearchBinding? = null

    private val binding get() = _binding!!

    private val viewModel: JobSearchViewModel by viewModels {
        Injector.provideJobSearchViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.jobTypeEditText.apply {
            this.doAfterTextChanged {
                val buttonShouldEnable = !it?.toString().isNullOrBlank()
                binding.jobSearchButton.isEnabled = buttonShouldEnable
            }
            this.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doOnSearchButtonClicked()
                    true
                } else false
            }
        }

        binding.jobSearchButton.setOnClickListener {
            doOnSearchButtonClicked()
        }

        binding.clearSearchHistoryView.setOnClickListener {
            doOnClearHistoryViewClicked()
        }

        val adapter = JobSearchHistoryListAdapter(mutableListOf()).apply {
            this.onItemClicked = this@JobSearchFragment::doOnSearchHistoryItemClicked
        }
        binding.searchHistoryRecyclerView.apply {
            this.adapter = adapter
            val flexboxLayoutManager =
                FlexboxLayoutManager(requireContext(), FlexDirection.ROW, FlexWrap.WRAP).apply {
                    // this.justifyContent = JustifyContent.FLEX_START
                }
            this.layoutManager = flexboxLayoutManager

            val flexboxItemDecoration = FlexboxItemDecoration(requireContext()).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.flexbox_divider_8)?.also { drawable ->
                    this.setDrawable(drawable)
                }
            }
            this.addItemDecoration(flexboxItemDecoration)
        }

        viewModel.jobSearchHistoryList.observe(viewLifecycleOwner) { historyList ->

            binding.clearSearchHistoryView.visibility = if (historyList.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }


            historyList?.also {
                log("SearchList", it.toString())

                adapter.setList(it)
            }
        }
    }

    private fun doOnSearchButtonClicked() {
        val jobType = binding.jobTypeEditText.text.toString()

        // insert or update job search history
        val history = JobSearchHistory(jobType, Date().time)
        viewModel.addJobSearchHistory(history)

        // navigate to search list fragment
        findNavController().navigate(JobSearchFragmentDirections.openJobSearchList(jobType))
    }

    private fun doOnSearchHistoryItemClicked(jobSearchHistory: JobSearchHistory) {
        binding.jobTypeEditText.bindText(jobSearchHistory.jobType)
    }

    private fun doOnClearHistoryViewClicked() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_all_search_history_dialog_title)
            .setMessage(R.string.delete_all_search_history_dialog_message)
            .setCancelable(true)
            .setPositiveButton(R.string.confirm) { _, _ -> viewModel.deleteAllHistory() }
            .setNegativeButton(R.string.cancel, null)
            .create()
            .show()
    }

}