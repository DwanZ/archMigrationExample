package com.example.archmigrationexample.view

import androidx.lifecycle.ViewModel
import com.example.archmigrationexample.util.ApiResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T : Any> : ViewModel(), CoroutineScope {

    private val parentJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    protected abstract val receiveChannel: ReceiveChannel<ApiResponse<T>>

    init {
        launch {
            receiveChannel.consumeEach {
                resolve(it)
            }
        }
    }

    abstract fun resolve(apiResponse: ApiResponse<T>)

    override val coroutineContext: CoroutineContext
        get() = parentJob + mainDispatcher

}