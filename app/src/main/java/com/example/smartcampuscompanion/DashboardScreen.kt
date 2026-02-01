package com.example.smartcampuscompanion

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DashboardScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Welcome, Student!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("campus") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Campus Information")
        }
    }
}
