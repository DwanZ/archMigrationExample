package com.example.archmigrationexample.util

sealed class Result<out T: Any> {

        class Success<out T: Any>(val data: T): Result<T>()

        class Error(val exception: Throwable): Result<Nothing>()

        fun handleResult(successBlock: (T) -> Unit = {}, failureBlock: (Throwable) -> Unit = {}) {
                when (this) {
                        is Success -> successBlock(data)
                        is Error -> failureBlock(exception)
                }
        }
}