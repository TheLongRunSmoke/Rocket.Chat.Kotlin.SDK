package chat.rocket.core.compat.internal

import chat.rocket.common.RocketChatException
import chat.rocket.core.compat.Call
import chat.rocket.core.compat.Callback
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.newCoroutineContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

@ExperimentalCoroutinesApi
@JvmOverloads
fun <T> callback(
    context: CoroutineContext = Dispatchers.Default,
    callback: Callback<T>,
    block: suspend CoroutineScope.() -> T
): Call {
    val newContext = GlobalScope.newCoroutineContext(context)
    val job = Job(newContext[Job])
    val coroutine = CallbackCoroutine(newContext + job, callback)
    block.startCoroutine(coroutine, coroutine)
    return Call(job)
}

@OptIn(InternalCoroutinesApi::class)
private class CallbackCoroutine<in T>(
    parentContext: CoroutineContext,
    private val callback: Callback<T>
) : AbstractCoroutine<T>(parentContext, true, true) {

    override fun onCompleted(value: T) {
        callback.onSuccess(value)
    }

    override fun onCancelled(cause: Throwable, handled: Boolean) {
        if (cause is RocketChatException) {
            callback.onError(cause)
        } else {
            callback.onError(RocketChatException(cause.message ?: "Unknown Error", cause))
        }
    }
}
