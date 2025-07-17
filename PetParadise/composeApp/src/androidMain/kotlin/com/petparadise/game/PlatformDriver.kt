package com.petparadise.game

import android.content.Context
import com.petparadise.game.data.DriverFactory

actual fun getPlatformDriverFactory(context: Any?): DriverFactory {
    return DriverFactory(context as Context)
}
