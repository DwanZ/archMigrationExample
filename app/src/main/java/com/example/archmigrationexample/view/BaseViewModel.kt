package com.example.archmigrationexample.view

import androidx.lifecycle.ViewModel
import com.example.archmigrationexample.util.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T : Any> : ViewModel(), CoroutineScope {

    private val parentJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    protected abstract val receiveChannel: Flow<ApiResponse<T>>

    init {
        launch {
            receiveChannel.collect {
                resolve(it)
            }
        }
    }

    abstract fun resolve(apiResponse: ApiResponse<T>)

    override val coroutineContext: CoroutineContext
        get() = parentJob + mainDispatcher

}