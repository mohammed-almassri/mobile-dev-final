package com.example.thefinal.feature.items.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.notthefinal.feature.items.domain.model.Item
import com.example.notthefinal.feature.items.ui.viewModel.ItemViewModel


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditItem(onDismissRequest: () -> Unit, itemListViewModel: ItemViewModel) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ){
        Surface {
            Column {
                var name by remember { mutableStateOf(itemListViewModel.currentItem.value!!.name) }
                var price by remember { mutableStateOf(itemListViewModel.currentItem.value!!.price.toString()) }
                var quantity by remember { mutableStateOf(itemListViewModel.currentItem.value!!.quantity.toString()) }
                var category by remember { mutableStateOf(itemListViewModel.currentItem.value!!.category) }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Row {
                    Button(
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            itemListViewModel.editItem(
                                Item(
                                    id = itemListViewModel.currentItem.value!!.id,
                                    name = name,
                                    category = category,
                                    price = price.toDouble(),
                                    quantity = quantity.toInt()
                                )
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}

