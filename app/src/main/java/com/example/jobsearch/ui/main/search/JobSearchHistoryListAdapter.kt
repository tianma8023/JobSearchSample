package com.example.jobsearch.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.jobsearch.data.db.entity.JobSearchHistory
import com.example.jobsearch.databinding.JobSearchHistoryItemBinding
import com.example.jobsearch.ui.base.BaseAdapter
import com.example.jobsearch.ui.base.BaseViewHolder
import com.example.jobsearch.util.bindText

class JobSearchHistoryListAdapter(
    searchHistoryList: MutableList<JobSearchHistory>
) : BaseAdapter<JobSearchHistory>(searchHistoryList) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<JobSearchHistory> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JobSearchHistoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        private val binding: JobSearchHistoryItemBinding
    ) : BaseViewHolder<JobSearchHistory>(binding.root) {

        override fun bindView(item: JobSearchHistory) {
            binding.jobSearchHistoryView.bindText(item.jobType)
        }
    }

}