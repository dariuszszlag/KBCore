package com.darek.kbcore.utils

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

class SwiftFlow <T: Any> internal constructor(
    internal val internalFlow: Flow<T>
){

    fun subscribe(
        next: (T) -> Unit,
        completion: (Throwable?) -> Unit
    ): () -> Unit {
        val job = MainScope().launch {
            internalFlow
                .onCompletion { completion(it) }
                .collect { next(it) }
        }
        return ({ job.cancel() })
    }

}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
val <T: Any> SwiftFlow<T>.kt: Flow<T> get() = internalFlow

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
internal val <T: Any> Flow<T>.mp: SwiftFlow<T> get() = SwiftFlow(this)
