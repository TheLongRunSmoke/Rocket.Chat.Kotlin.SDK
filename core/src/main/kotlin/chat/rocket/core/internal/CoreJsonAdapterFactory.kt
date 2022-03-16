package chat.rocket.core.internal

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
object CoreJsonAdapterFactory : JsonAdapter.Factory by KotshiCoreJsonAdapterFactory
