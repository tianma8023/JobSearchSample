package com.example.jobsearch.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _loadingStatus = MutableLiveData<Boolean>()

    val loadingStatus: LiveData<Boolean>
        get() = _loadingStatus

    protected val _errorMsg = MutableLiveData<String?>()

    val errorMsg: LiveData<String?>
        get() = _errorMsg

    fun launchDataLoad(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            try {
                // loading
                _loadingStatus.postValue(true)
                block.invoke(this)
            } catch (e: ApolloException) {
                _errorMsg.postValue(e.message)
            } finally {
                _loadingStatus.postValue(false)
            }
        }
    }
}