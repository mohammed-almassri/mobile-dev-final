package com.example.notthefinal.feature.items.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notthefinal.feature.items.data.local.ItemDatabase
import com.example.notthefinal.feature.items.data.repository.OfflineItemsRepository
import com.example.notthefinal.feature.items.domain.model.Item
import com.example.notthefinal.feature.items.ui.viewModel.ItemViewModel
import com.example.thefinal.feature.items.ui.component.AddItem
import com.example.thefinal.feature.items.ui.component.EditItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(modifier: Modifier = Modifier,itemListViewModel: ItemViewModel,navigateToItemDetails: (id:Int)->Unit) {
    val context = LocalContext.current

    val itemListUiState by itemListViewModel.itemListUiState.collectAsStateWithLifecycle()
    var addItemDialog by remember { mutableStateOf(false) }
    var editItemDialog by remember { mutableStateOf(false) }

    val addItemUiState by itemListViewModel.addItemUiState.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addItemDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val context = LocalContext.current
        // Show error exactly once
        LaunchedEffect(addItemUiState.error) {
            addItemUiState.error?.let { msg ->
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                itemListViewModel.clearError()
            }
        }

        // Show success exactly once and then close the dialog
        LaunchedEffect(addItemUiState.success) {
            addItemUiState.success?.let {
                Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show()
                itemListViewModel.consumeSuccess()
                addItemDialog = false
            }
        }
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
        ) {
            itemsIndexed(itemListUiState.items) { index, item ->
                   ListItem(
                       modifier= Modifier.clickable{
                           itemListViewModel.setCurrentItem(itemListUiState.items[index])
                           navigateToItemDetails(item.id)},
                       headlineContent = { Text(text = item.name) },

                       supportingContent = {
                           Row(
                               modifier = Modifier.fillMaxWidth()
                           ) {
                               Text(text = "Price: ${item.price}", modifier = Modifier.weight(1f))
                               Text(text = "Quantity: ${item.quantity}", modifier = Modifier.weight(1f))
                           }
                       },
                       trailingContent = {
                           Row {
                               IconButton(
                                   onClick = {
                                       itemListViewModel.setCurrentItem(itemListUiState.items[index])
                                       editItemDialog = true
                                   }
                               ) {
                                   Icon(
                                       imageVector = Icons.Default.Edit,
                                       contentDescription = "Edit"
                                   )
                               }
                               IconButton(
                                   onClick = {
                                       itemListViewModel.deleteItem(itemListUiState.items[index])
                                   }
                               ) {
                                   Icon(
                                       imageVector = Icons.Default.Delete,
                                       contentDescription = "Delete"
                                   )
                               }
                           }
                       },
                   )
            }
        }
        if (addItemDialog) {
            AddItem(onDismissRequest = { addItemDialog = false }, itemListViewModel = itemListViewModel)
        }
        if (editItemDialog) {
            EditItem(onDismissRequest = { editItemDialog = false }, itemListViewModel = itemListViewModel)
        }
    }
}
