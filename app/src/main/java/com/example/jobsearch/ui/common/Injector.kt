package com.example.jobsearch.ui.common

import android.content.Context
import com.example.jobsearch.data.db.AppDatabase
import com.example.jobsearch.data.repository.JobSearchHistoryRepository
import com.example.jobsearch.ui.main.search.JobSearchViewModelFactory

interface ViewModelFactoryProvider {

    fun provideJobSearchViewModelFactory(context: Context): JobSearchViewModelFactory

}

private object DefaultViewModelProvider : ViewModelFactoryProvider {

    private fun getAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    override fun provideJobSearchViewModelFactory(context: Context): JobSearchViewModelFactory {
        val dao = getAppDatabase(context).jobSearchHistoryDao()
        val repository = JobSearchHistoryRepository(dao)
        return JobSearchViewModelFactory(repository)
    }

}

val Injector : ViewModelFactoryProvider
    get() = currentInjector

private val currentInjector: ViewModelFactoryProvider = DefaultViewModelProvider