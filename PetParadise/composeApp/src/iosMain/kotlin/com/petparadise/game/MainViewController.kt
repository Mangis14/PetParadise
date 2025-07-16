package com.quoders.kmp.template

import platform.UIKit.UIViewController

fun MainViewController() = ComposeUIViewController {
    initKoin(driverFactory = DriverFactory())
    App(driverFactory = DriverFactory())
}