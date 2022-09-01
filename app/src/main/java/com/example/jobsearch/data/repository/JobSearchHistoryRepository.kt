package com.example.jobsearch.data.repository

import com.example.jobsearch.data.db.dao.JobSearchHistoryDao
import com.example.jobsearch.data.db.entity.JobSearchHistory

class JobSearchHistoryRepository(private val jobSearchHistoryDao: JobSearchHistoryDao) {

    val jobSearchHistoryList = jobSearchHistoryDao.getJobSearchHistoryList()

    suspend fun insertJobSearchHistory(jobSearchHistory: JobSearchHistory) {
        jobSearchHistoryDao.insertJobSearchHistory(jobSearchHistory)
        jobSearchHistoryDao.removeOldHistory()
    }

    suspend fun deleteAllHistory() {
        jobSearchHistoryDao.deleteAllHistory()
    }

}