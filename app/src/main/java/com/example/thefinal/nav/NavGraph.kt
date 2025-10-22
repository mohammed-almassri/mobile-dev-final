package com.example.thefinal.nav


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.bright.easylogin.LoginScreen
import com.example.notthefinal.feature.items.data.local.ItemDatabase
import com.example.notthefinal.feature.items.data.repository.OfflineItemsRepository
import com.example.notthefinal.feature.items.ui.screen.ItemScreen
import com.example.notthefinal.feature.items.ui.viewModel.ItemViewModel
import com.example.thefinal.feature.items.ui.screen.CategoryScreen
import com.example.thefinal.feature.items.ui.screen.ItemDetailsScreen
import com.example.thefinal.feature.settings.ui.screens.SettingsScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack<AppNavKey>(Login)

    // Determine if the bottom bar should be shown
    val showBottomBar = when (backStack.lastOrNull()) {
        Login -> false // Screens WITHOUT a bottom bar
        else -> true // Screens WITH a bottom bar (Category, Settings, etc.)
    }

    Scaffold(
        bottomBar = {
            // Only show the NavigationBar if showBottomBar is true
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        label = { Text("Category") },
                        selected = backStack.lastOrNull() == Category,
                        onClick = {
                            backStack.clear()
                            backStack.add(Category)
                        },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = "Categories")
                        }
                    )
                    NavigationBarItem(
                        label = { Text("Settings") },
                        selected = backStack.lastOrNull() == Settings,
                        onClick = {
                            // This logic is a bit redundant. Clearing and adding once is enough.
                            backStack.clear()
                            backStack.add(Settings)
                        },
                        icon = {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val context = LocalContext.current

        val itemListViewModel: ItemViewModel = viewModel {
            ItemViewModel(OfflineItemsRepository(ItemDatabase.getDatabase(context).itemDao()))
        }
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry <Login>{
                    LoginScreen(navigateToCategories = {
                        backStack.clear()
                        backStack.add(Category)
                    })
                }
                entry<Items> {
                    ItemScreen(
                        modifier = modifier,
                        itemListViewModel,
                        navigateToItemDetails = {
                            backStack.add(ItemDetails)
                        }
                    )
                }
                entry<ItemDetails> {
                    ItemDetailsScreen(
                        modifier = modifier,
                        itemListViewModel
                    )
                }
                entry<Category> {
                    CategoryScreen(
                        modifier = modifier,
                        itemListViewModel = itemListViewModel,
                        navigateToItemScreen = ({ cat ->
                            // This clears the history, which might be what you want.
                            // If you want to be able to go "back" to the category screen,
                            // you should just use backStack.add()
                            backStack.clear()
                            backStack.add(Items(cat))
                        })
                    )
                }

                entry<Settings> {
                    SettingsScreen(modifier)
                }
            },
            modifier = modifier
                .padding(innerPadding)
        )
    }
}
