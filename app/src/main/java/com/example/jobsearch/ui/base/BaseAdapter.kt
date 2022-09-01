package com.example.jobsearch.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    private val items: MutableList<T>
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var onItemClicked: ((T) -> Unit)? = null

    private fun itemAt(position: Int): T {
        return items[position]
    }

    protected fun items(): MutableList<T> {
        return items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = itemAt(position)
        holder.bindView(item)
        bindItemClickListener(holder, item)
    }

    protected fun bindItemClickListener(holder: BaseViewHolder<T>, item: T) {
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(item)
        }
    }

    fun setList(newList: List<T>) {
        val originSize = itemCount
        items.clear()
        items.addAll(newList)
        val curSize = itemCount
        notifyItemRangeChanged(0, maxOf(originSize, curSize))
    }

    fun addList(list: List<T>) {
        if (list.isNotEmpty()) {
            val positionStart = itemCount
            items.addAll(list)
            notifyItemRangeChanged(positionStart, list.size)
        }
    }

}