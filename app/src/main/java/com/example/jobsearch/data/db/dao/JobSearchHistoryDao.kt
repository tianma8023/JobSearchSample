package com.example.jobsearch.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobsearch.data.db.entity.JobSearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface JobSearchHistoryDao {

    companion object {
        private const val HISTORY_MAX_COUNT = 5
    }

    @Query("SELECT * FROM job_search_history ORDER BY timestamp DESC LIMIT $HISTORY_MAX_COUNT")
    fun getJobSearchHistoryList(): Flow<List<JobSearchHistory>>

    @Query(
        """
            DELETE FROM job_search_history 
            WHERE job_type NOT IN (
                SELECT job_type FROM job_search_history ORDER BY timestamp DESC LIMIT $HISTORY_MAX_COUNT
            )
        """
    )
    suspend fun removeOldHistory()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobSearchHistory(jobSearchHistory: JobSearchHistory)

    @Query("DELETE FROM job_search_history")
    suspend fun deleteAllHistory()

}