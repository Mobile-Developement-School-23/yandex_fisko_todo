package com.example.todoappfisko.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Switch
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun MainActivity() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* обработка клика на первую кнопку */ }
            ) {
                Text("Сохранить")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { /* обработка клика на вторую кнопку */ }
            ) {
                Text("Отмена")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        EditText()
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text("Важность")
            Dropdown()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            Text("Открыть календарь")
            SwitchWithCalendar()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* обработка клика на кнопку "Удалить" */ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Удалить")
        }
    }
}

@Composable
fun EditText() {
    TextField(
        value = "",
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 3 * TextFieldDefaults.MinHeight),
        onValueChange = { /* обработчик изменения текста */ },
        label = { Text("Введите текст") },
        maxLines = 3
    )
}

@Composable
fun Dropdown() {
    val options = listOf("Low", "Normal", "Urgent")
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.background(Color.LightGray)) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = options[selectedIndex]
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = false,
            onDismissRequest = {},
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun SwitchWithCalendar() {
    var isSwitchChecked by remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf("") }
    val isCalendarVisible = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = isSwitchChecked, onCheckedChange = { isSwitchChecked = it })
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Открыть календарь"
        )
    }

    if (isSwitchChecked) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    isCalendarVisible.value = true
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Выбрать дату")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(selectedDate.value, modifier = Modifier.padding(top = 8.dp))
        }
        if (isCalendarVisible.value) {
            // Код для отображения календаря
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainActivity()
}