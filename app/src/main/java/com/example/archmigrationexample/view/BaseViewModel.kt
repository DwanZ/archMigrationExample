package com.example.archmigrationexample.view

import androidx.lifecycle.ViewModel
import com.example.archmigrationexample.data.entity.Entity
import com.example.archmigrationexample.util.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class BaseViewModel: ViewModel(), CoroutineScope {

    private val parentJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    protected abstract val receiveChannel: ReceiveChannel<Result<Entity>>

    init {
        launch {
            receiveChannel.consumeEach {
                resolve(it)
            }
        }
    }

    abstract fun resolve(result: Result<Entity>)

    override val coroutineContext: CoroutineContext
        get() = parentJob + mainDispatcher

}