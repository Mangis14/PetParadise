package com.petparadise.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.petparadise.game.data.DriverFactory
import com.petparadise.game.di.initKoin
import com.petparadise.game.feature.GameViewModel
import com.petparadise.game.feature.Pet
import com.petparadise.game.feature.Upgrade
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

expect fun getPlatformDriverFactory(context: Any? = null): DriverFactory

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        val context = LocalContext.current
        initKoin(driverFactory = getPlatformDriverFactory(context))
    }) {
        MaterialTheme {
            val viewModel: GameViewModel = koinInject()
            val petCoins by viewModel.petCoins.collectAsState()
            val ownedPets by viewModel.ownedPets.collectAsState()

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Pet Coins: $petCoins")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.onPetTapped() }) {
                    Text("Tap Pet!")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Owned Pets:")
                LazyColumn {
                    items(ownedPets) { pet ->
                        Text("${pet.name} (${pet.coinGenerationRate} coins/sec)")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Available Pets:")
                LazyColumn {
                    items(viewModel.availablePets) { pet ->
                        Row {
                            Text("${pet.name} - 10 coins")
                            Button(onClick = { viewModel.purchasePet(pet) }) {
                                Text("Buy")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Available Upgrades:")
                LazyColumn {
                    items(viewModel.availableUpgrades) { upgrade ->
                        Row {
                            Text("${upgrade.name} - ${upgrade.cost} coins")
                            Button(onClick = { viewModel.purchaseUpgrade(upgrade) }) {
                                Text("Buy")
                            }
                        }
                    }
                }
            }
        }
    }
}