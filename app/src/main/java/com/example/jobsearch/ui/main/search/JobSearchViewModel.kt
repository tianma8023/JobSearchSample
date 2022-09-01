package com.example.jobsearch.ui.main.search

import androidx.lifecycle.*
import com.example.jobsearch.data.db.entity.JobSearchHistory
import com.example.jobsearch.data.repository.JobSearchHistoryRepository
import kotlinx.coroutines.launch

class JobSearchViewModel(private val repository: JobSearchHistoryRepository) : ViewModel() {

    val jobSearchHistoryList: LiveData<List<JobSearchHistory>> =
        repository.jobSearchHistoryList.asLiveData()

    fun addJobSearchHistory(jobSearchHistory: JobSearchHistory) {
        viewModelScope.launch {
            repository.insertJobSearchHistory(jobSearchHistory)
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            repository.deleteAllHistory()
        }
    }

}

class JobSearchViewModelFactory(private val repository: JobSearchHistoryRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JobSearchViewModel(repository) as T
    }
}