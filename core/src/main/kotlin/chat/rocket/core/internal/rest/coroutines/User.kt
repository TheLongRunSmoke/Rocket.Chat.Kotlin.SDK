package chat.rocket.core.internal.rest.coroutines

import chat.rocket.core.RocketChatClient
import chat.rocket.core.internal.rest.me
import chat.rocket.core.model.Myself
import kotlin.coroutines.experimental.suspendCoroutine

suspend fun RocketChatClient.me(): Myself =
        suspendCoroutine { continuation ->
            me(success = {
                continuation.resume(it)
            }, error = {
                continuation.resumeWithException(it)
            })
        }
