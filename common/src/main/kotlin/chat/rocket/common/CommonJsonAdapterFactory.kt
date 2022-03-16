package chat.rocket.common

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
object CommonJsonAdapterFactory : JsonAdapter.Factory by KotshiCommonJsonAdapterFactory