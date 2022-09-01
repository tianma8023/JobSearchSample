package com.example.jobsearch.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.jobsearch.apollo.JobListQuery
import com.example.jobsearch.databinding.JobSearchListItemBinding
import com.example.jobsearch.ui.base.BaseAdapter
import com.example.jobsearch.ui.base.BaseViewHolder
import com.example.jobsearch.util.bindText

class JobListAdapter(
    jobs: MutableList<JobListQuery.Job>
) : BaseAdapter<JobListQuery.Job>(jobs) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JobSearchListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(
        private val binding: JobSearchListItemBinding
    ) : BaseViewHolder<JobListQuery.Job>(binding.root) {

        override fun bindView(item: JobListQuery.Job) {
            binding.jobTitleView.bindText(item.title)
            binding.companyNameView.bindText(item.company?.name)
        }
    }

}