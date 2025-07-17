package com.petparadise.game.data

import app.cash.sqldelight.db.SqlDriver
import com.petparadise.game.cache.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}
