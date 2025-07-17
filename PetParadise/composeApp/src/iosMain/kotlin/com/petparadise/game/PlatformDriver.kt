package com.petparadise.game

import com.petparadise.game.data.DriverFactory

actual fun getPlatformDriverFactory(context: Any?): DriverFactory {
    return DriverFactory()
}
