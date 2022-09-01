package com.example.jobsearch.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsearch.apollo.JobListQuery
import com.example.jobsearch.service.apolloClient
import com.example.jobsearch.ui.base.BaseViewModel

/**
 * View model of searched job list
 */
class JobListViewModel : BaseViewModel() {

    private val _jobList = MutableLiveData<List<JobListQuery.Job>>()

    val jobList: LiveData<List<JobListQuery.Job>>
        get() = _jobList

    fun searchJobList(jobType: String) {
        launchDataLoad {
            val response = apolloClient.query(JobListQuery(jobType)).execute()
            val jobs = response.data?.jobs
            if (jobs == null || response.hasErrors()) {
                val errorMsg = response.errors?.firstOrNull()?.message
                    ?: "Network error!"
                _errorMsg.postValue(errorMsg)
                return@launchDataLoad
            }
            _jobList.postValue(jobs)
        }
    }

}