package com.petparadise.game

import platform.UIKit.UIViewController

fun MainViewController() = ComposeUIViewController {
    initKoin(driverFactory = DriverFactory())
    App(driverFactory = DriverFactory())
}