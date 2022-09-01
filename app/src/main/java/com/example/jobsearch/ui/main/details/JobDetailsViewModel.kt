package com.example.jobsearch.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.jobsearch.apollo.JobQuery
import com.example.jobsearch.service.apolloClient
import com.example.jobsearch.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * View model of job details
 */
class JobDetailsViewModel : BaseViewModel() {

    private val _job = MutableLiveData<JobQuery.Job>()

    val job: LiveData<JobQuery.Job> get() = _job

    fun queryJob(companySlug: String, jobSlug: String) {

        launchDataLoad {
            val response = apolloClient.query(JobQuery(companySlug, jobSlug)).execute()
            val job = response.data?.job
            if (job == null || response.hasErrors()) {
                val errorMsg = response.errors?.firstOrNull()?.message
                    ?: "Network error!"
                _errorMsg.postValue(errorMsg)
                return@launchDataLoad
            }
            _job.postValue(job)
        }
    }

}