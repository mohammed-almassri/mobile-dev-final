package com.example.thefinal.feature.items.ui.screen

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun CategoryScreen(modifier: Modifier,navigateToItemScreen: (cat:String) -> Unit,itemListViewModel: ItemViewModel) {
    val context = LocalContext.current
    val categoriesViewModel: CategoryViewModel = viewModel {
        CategoryViewModel(OfflineItemsRepository(ItemDatabase.getDatabase(context).itemDao()))
    }
    val categoryListUiState by categoriesViewModel.categoryListUiState.collectAsStateWithLifecycle()
    var addItemDialog by remember { mutableStateOf(false) }


    val jokeApiService = remember { ApiProvider.jokeApiService }
    val jokeViewModel: JokeViewModel = viewModel {
        JokeViewModel(JokeRepositoryImpl(jokeApiService))
    }


    val jokeUiState by jokeViewModel.uiState.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addItemDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { innerPadding ->
        Column(){

        if(jokeUiState.joke!=null){
            Text("Today's Joke", fontSize = 32.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))

            Column {
                Text(text = jokeUiState.joke!!.setup)
                Text(text = jokeUiState.joke!!.punchline)
            }
            Spacer(
                modifier = Modifier.height(50.dp
                ))
        }
        if (categoryListUiState.error != null)
            Text(text = "Error")

       else  if(categoryListUiState.isLoading){
            Text(text = "Loading...")
        }
        else if(categoryListUiState.items.size==0){
            Text("NO CATEGORIES")
        }
        else{
        Text("Categories", fontSize = 32.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            categoryListUiState.items.forEach { category ->
                Row(modifier = Modifier
                    .clickable {
                        itemListViewModel.setCategory(category)
                        navigateToItemScreen(category)
                    }
                    .padding(8.dp)) {
                    Text(text = category)
                }
            }
        }
        if (addItemDialog) {
            AddItem(onDismissRequest = { addItemDialog = false }, itemListViewModel = itemListViewModel)
        }
        }

    }
}}