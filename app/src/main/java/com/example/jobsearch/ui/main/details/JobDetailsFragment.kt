package com.example.jobsearch.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.jobsearch.R
import com.example.jobsearch.apollo.JobQuery
import com.example.jobsearch.databinding.FragmentJobDetailsBinding
import com.example.jobsearch.util.bindText
import com.example.jobsearch.util.log
import com.google.android.material.snackbar.Snackbar

class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: JobDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<JobDetailsViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeViewModel()
        viewModel.queryJob(companySlug = args.companySlug, jobSlug = args.jobSlug)
    }

    private fun subscribeViewModel() {
        viewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.also {
                Snackbar.make(binding.root, errorMsg, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.job.observe(viewLifecycleOwner) { job ->
            refreshUI(job)
        }
    }

    private fun refreshUI(job: JobQuery.Job) {
        // job title
        binding.jobTitleView.bindText(job.title)

        // job description
        binding.jobDescriptionView.bindText(job.description)

        // company logo
        val logoUrl = job.company?.logoUrl
        log("JobDetails", "logoUrl: $logoUrl")
        binding.companyLogoView.load(job.company?.logoUrl) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.splash_screen_background)
            error(R.drawable.splash_screen_background)
        }

        // company name
        binding.companyNameView.bindText(job.company?.name)
    }

}