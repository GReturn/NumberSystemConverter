package com.example.numbersystemconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(radix: List<String>, modifier: Modifier = Modifier, onSelectionChanged: (String, String) -> Unit) {
    var selectedRadixFrom by remember { mutableStateOf(radix[0]) }
    var selectedRadixTo by remember { mutableStateOf(radix[2]) }
    var isExpandedFrom by remember { mutableStateOf(false) }
    var isExpandedTo by remember { mutableStateOf(false) }

    val dropdownWidth = 180.dp
    val dropdownHeight = 130.dp
    val dropdownPadding = 8.dp

    val fontSize = 20.sp

    Row (
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .width(dropdownWidth)
                .height(dropdownHeight)
                .padding(dropdownPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "From", fontSize = fontSize)
            ExposedDropdownMenuBox(
                modifier = Modifier.width(dropdownWidth - 50.dp),
                expanded = isExpandedFrom,
                onExpandedChange = { isExpandedFrom = !isExpandedFrom }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    value = selectedRadixFrom,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedFrom) }
                )
                ExposedDropdownMenu(
                    expanded = isExpandedFrom,
                    onDismissRequest = { isExpandedFrom = false }
                ) {
                    radix.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            text = { Text(text = s) },
                            onClick = {
                                selectedRadixFrom = radix[index]
                                isExpandedFrom = false
                                onSelectionChanged(selectedRadixFrom, selectedRadixTo)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .width(dropdownWidth)
                .height(dropdownHeight)
                .padding(dropdownPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "To", fontSize = fontSize)
            ExposedDropdownMenuBox(
                modifier = Modifier.width(dropdownWidth - 50.dp),
                expanded = isExpandedTo,
                onExpandedChange = { isExpandedTo = !isExpandedTo }
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(),
                    value = selectedRadixTo,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedTo) }
                )
                ExposedDropdownMenu(
                    expanded = isExpandedTo,
                    onDismissRequest = { isExpandedTo = false }
                ) {
                    radix.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            text = { Text(text = s) },
                            onClick = {
                                selectedRadixTo = radix[index]
                                isExpandedTo = false
                                onSelectionChanged(selectedRadixFrom, selectedRadixTo)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
    }
}