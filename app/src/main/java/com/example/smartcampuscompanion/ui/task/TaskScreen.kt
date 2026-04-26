package com.example.smartcampuscompanion.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.data.local.TaskEntity
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen
import java.util.Calendar

@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    navController: NavHostController
) {
    val tasks by viewModel.tasks.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf("Select Date & Time") }
    var editingTaskId by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<TaskEntity?>(null) }

    val completedCount = tasks.count { it.isCompleted }
    val calendar = Calendar.getInstance()
    val gradient = Brush.linearGradient(listOf(DarkGreen, MediumGreen))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8F7))
    ) {

        // 🔹 HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(top = 52.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0x1AFFFFFF), CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        "Task Manager",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (tasks.isEmpty()) "No tasks yet"
                        else "$completedCount of ${tasks.size} completed",
                        color = Color(0xB3FFFFFF)
                    )
                }
            }
        }

        // 🔹 CONTENT
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 🔹 FORM
            item {
                Card(shape = RoundedCornerShape(18.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            if (editingTaskId == 0) "New Task" else "Edit Task",
                            color = DarkGreen,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it; errorMessage = "" },
                            label = { Text("Task Title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
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
                                                selectedDateTime =
                                                    "$day/${month + 1}/$year $hour:$minute"
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

                        if (errorMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(errorMessage, color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                when {
                                    title.isBlank() ->
                                        errorMessage = "Please enter title"
                                    selectedDateTime == "Select Date & Time" ->
                                        errorMessage = "Select date/time"
                                    else -> {
                                        if (editingTaskId == 0) {
                                            viewModel.addTask(
                                                TaskEntity(
                                                    title = title,
                                                    description = description,
                                                    dateTime = selectedDateTime
                                                )
                                            )
                                        } else {
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
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MediumGreen)
                        ) {
                            Text("Save", color = Color.White)
                        }
                    }
                }
            }

            // 🔹 EMPTY STATE
            if (tasks.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Rounded.CheckCircle,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No tasks yet", color = Color.Gray)
                    }
                }
            }

            // 🔹 TASK LIST
            items(tasks, key = { it.id }) { task ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (task.isCompleted) Color(0xFFF0F0F0) else Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = {
                                viewModel.toggleTaskCompletion(task)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MediumGreen
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {

                            Text(
                                task.title,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = if (task.isCompleted)
                                    TextDecoration.LineThrough else null,
                                color = if (task.isCompleted)
                                    Color.Gray else DarkGreen
                            )

                            Text(
                                "Due: ${task.dateTime}",
                                color = MediumGreen
                            )
                        }

                        IconButton(onClick = {
                            title = task.title
                            description = task.description
                            selectedDateTime = task.dateTime
                            editingTaskId = task.id
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = "Edit")
                        }

                        IconButton(onClick = {
                            taskToDelete = task
                            showDeleteDialog = true
                        }) {
                            Icon(
                                Icons.Rounded.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    // 🔹 DELETE DIALOG
    if (showDeleteDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Task") },
            text = { Text("Delete \"${taskToDelete?.title}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    taskToDelete?.let { viewModel.deleteTask(it) }
                    showDeleteDialog = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}