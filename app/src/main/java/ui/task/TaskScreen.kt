package com.example.smartcampuscompanion.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.smartcampuscompanion.data.local.TaskEntity
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.util.Calendar

@Composable
fun TaskScreen(viewModel: TaskViewModel) {

    val tasks by viewModel.tasks.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf("Select Date & Time") }

    val calendar = Calendar.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Task Manager",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                selectedDateTime =
                                    "$day/${month + 1}/$year $hour:$minute"
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        ) {
            Text(selectedDateTime)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.addTask(
                    TaskEntity(
                        title = title,
                        description = description,
                        dateTime = selectedDateTime
                    )
                )
                title = ""
                description = ""
                selectedDateTime = "Select Date & Time"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(task.description)
                        Text("Due: ${task.dateTime}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.deleteTask(task) }
                        ) {
                            Text("Delete")
                        }
                        Button(
                            onClick = {
                                viewModel.updateTask(
                                    task.copy(title = task.title + " (Edited)")
                                )
                            }
                        ) {
                            Text("Edit")
                        }

                    }
                }
            }
        }
    }
}
