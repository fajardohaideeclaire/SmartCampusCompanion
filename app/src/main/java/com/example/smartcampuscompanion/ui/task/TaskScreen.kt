package com.example.smartcampuscompanion.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.data.local.TaskEntity
import com.example.smartcampuscompanion.ui.theme.MediumGreen // Added for the progress indicator color
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.util.Calendar
// Added Import
import androidx.compose.material3.LinearProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel, navController: NavHostController) {
    val tasks by viewModel.tasks.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf("Select Date & Time") }
    var editingTaskId by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf("") }

    // Delete confirmation dialog state
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Manager", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    errorMessage = ""
                },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorMessage.isNotEmpty()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Task Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val h = hour.toString().padStart(2, '0')
                                    val m = minute.toString().padStart(2, '0')
                                    selectedDateTime = "$day/${month + 1}/$year $h:$m"
                                    errorMessage = ""
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
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedDateTime)
            }

            // Error message
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    when {
                        title.isBlank() -> {
                            errorMessage = "Please enter a task title"
                        }
                        selectedDateTime == "Select Date & Time" -> {
                            errorMessage = "Please select date and time"
                        }
                        else -> {
                            if (editingTaskId == 0) {
                                // Add new task
                                viewModel.addTask(
                                    TaskEntity(
                                        title = title,
                                        description = description,
                                        dateTime = selectedDateTime
                                    )
                                )
                            } else {
                                // Update existing task
                                viewModel.updateTask(
                                    TaskEntity(
                                        id = editingTaskId,
                                        title = title,
                                        description = description,
                                        dateTime = selectedDateTime
                                    )
                                )
                                editingTaskId = 0
                            }
                            title = ""
                            description = ""
                            selectedDateTime = "Select Date & Time"
                            errorMessage = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editingTaskId == 0) "Add Task" else "Update Task")
            }

            // Show cancel button when editing
            if (editingTaskId != 0) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        editingTaskId = 0
                        title = ""
                        description = ""
                        selectedDateTime = "Select Date & Time"
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel Edit")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // UI Placeholder for Loading Indicator
            // Replace 'false' with 'isLoading' once Dacillo updates the TaskViewModel
            if (false) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MediumGreen,
                    trackColor = Color(0xFFCCCCCC)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (tasks.isEmpty()) {
                // Better empty state
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFFCCCCCC),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No tasks yet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF888888)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Add your first task above to get started!",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFAAAAAA),
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tasks, key = { it.id }) { task ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Completion checkbox
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { viewModel.toggleTaskCompletion(task) }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                                    color = if (task.isCompleted) Color(0xFF888888) else Color.Unspecified
                                )
                                if (task.description.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = task.description,
                                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                                        color = if (task.isCompleted) Color(0xFF888888) else Color.Unspecified
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Due: ${task.dateTime}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(
                                        onClick = {
                                            // Pre-fill form with task data for editing
                                            title = task.title
                                            description = task.description
                                            selectedDateTime = task.dateTime
                                            editingTaskId = task.id
                                        }
                                    ) {
                                        Text("Edit")
                                    }
                                    OutlinedButton(
                                        onClick = {
                                            taskToDelete = task
                                            showDeleteDialog = true
                                        }
                                    ) {
                                        Text("Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Task") },
            text = { Text("Are you sure you want to delete \"${taskToDelete?.title}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskToDelete?.let { viewModel.deleteTask(it) }
                        showDeleteDialog = false
                        taskToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}