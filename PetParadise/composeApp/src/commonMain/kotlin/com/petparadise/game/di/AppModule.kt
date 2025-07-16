package com.petparadise.game.di

import com.petparadise.game.feature.GameViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}, driverFactory: DriverFactory) = startKoin {
    appDeclaration()
    modules(
        module {
            single { createDatabase(driverFactory) }
            single { LocalDataSource(get()) }
            single { GameRepository(get()) }
            viewModel { GameViewModel(get()) }
        }
    )
}