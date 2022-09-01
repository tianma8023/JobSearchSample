package com.example.jobsearch.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearch.apollo.JobListQuery
import com.example.jobsearch.databinding.FragmentJobSearchListBinding
import com.example.jobsearch.util.log
import com.google.android.material.snackbar.Snackbar

/**
 * Job search result list fragment
 */
class JobSearchListFragment : Fragment() {

    private var _binding: FragmentJobSearchListBinding? = null

    private val binding get() = _binding!!

    private val args: JobSearchListFragmentArgs by navArgs()

    private val viewModel: JobListViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private val listAdapter by lazy {
        JobListAdapter(mutableListOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("JobList", "onCreate ${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("JobList", "onCreateView ${hashCode()}")
        _binding = FragmentJobSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log("JobList", "onViewCreated")

        initUI()
        subscribeToViewModel()
        viewModel.searchJobList(args.jobType)
    }

    private fun initUI() {
        listAdapter.onItemClicked = this::routeToJobDetailsPage

        binding.jobListRecyclerView.apply {
            this.adapter = listAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.swipeRefreshLayout.isEnabled = true
    }

    private fun routeToJobDetailsPage(job: JobListQuery.Job) {
        val companySlug = job.company?.slug ?: ""
        val jobSlug = job.slug
        findNavController().navigate(JobSearchListFragmentDirections.openJobDetails(companySlug, jobSlug))
    }

    private fun subscribeToViewModel() {
        viewModel.errorMsg.observe(viewLifecycleOwner) { text ->
            text?.also {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
                binding.swipeRefreshLayout.isEnabled = true
            }
        }

        viewModel.jobList.observe(viewLifecycleOwner) { jobList ->
            log("JobList", "$jobList")
            binding.swipeRefreshLayout.isEnabled = false
            listAdapter.setList(jobList)
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

}