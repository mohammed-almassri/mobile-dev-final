package com.example.thefinal.feature.items.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notthefinal.core.network.ApiProvider
import com.example.notthefinal.feature.items.data.local.ItemDatabase
import com.example.notthefinal.feature.items.data.repository.OfflineItemsRepository
import com.example.notthefinal.feature.items.ui.viewModel.ItemViewModel
import com.example.notthefinal.feature.posts.data.repository.impl.JokeRepositoryImpl
import com.example.notthefinal.feature.posts.ui.viewModel.JokeViewModel
import com.example.thefinal.feature.items.ui.component.AddItem
import com.example.thefinal.feature.items.ui.viewModel.CategoryViewModel

@Composable
fun ItemDetailsScreen(modifier: Modifier,itemListViewModel: ItemViewModel) {
    val context = LocalContext.current
    val item by itemListViewModel.currentItem.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),

    ) { innerPadding ->
        ListItem(
            headlineContent = { Text(text = item!!.name) },
            supportingContent = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Price: ${item!!.price}", modifier = Modifier.weight(1f))
                    Text(text = "Quantity: ${item!!.quantity}", modifier = Modifier.weight(1f))
                }
            },
        )

    }}