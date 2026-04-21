package com.example.smartcampuscompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@Composable
fun AdminScreen(
    navController: NavHostController,
    announcementViewModel: AnnouncementViewModel
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val authUtils = remember { AuthUtils() }

    val isLoading by announcementViewModel.isLoading.collectAsState()
    val errorMessage by announcementViewModel.errorMessage.collectAsState()
    val postSuccess by announcementViewModel.postSuccess.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("General") }
    var titleError by remember { mutableStateOf("") }
    var contentError by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val categories = listOf("General", "Academic", "Advisory", "Events", "Facilities")
    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    // Show success dialog when post succeeds
    LaunchedEffect(postSuccess) {
        if (postSuccess) {
            showSuccessDialog = true
            announcementViewModel.clearPostSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8F7))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(top = 52.dp, bottom = 32.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Admin badge
                Box(
                    modifier = Modifier
                        .background(Color(0x26FFFFFF), RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ADMIN",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = Color.White
                    )
                }

                // Logout
                Icon(
                    Icons.Rounded.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xCCFFFFFF),
                    modifier = Modifier
                        .size(22.dp)
                        .clickable {
                            authUtils.signOut()
                            sessionManager.clearSession()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0x26FFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Admin Dashboard",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = sessionManager.getUsername(),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xB3FFFFFF),
                textAlign = TextAlign.Center
            )
        }

        // Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Post Announcement card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Post New Announcement",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = DarkGreen
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title field
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it; titleError = "" },
                        label = { Text("Announcement Title") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = titleError.isNotEmpty(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MediumGreen,
                            focusedLabelColor = MediumGreen,
                            focusedTextColor = DarkGreen,
                            unfocusedTextColor = DarkGreen
                        )
                    )
                    if (titleError.isNotEmpty()) {
                        Text(
                            text = titleError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Content field
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it; contentError = "" },
                        label = { Text("Announcement Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                        isError = contentError.isNotEmpty(),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MediumGreen,
                            focusedLabelColor = MediumGreen,
                            focusedTextColor = DarkGreen,
                            unfocusedTextColor = DarkGreen
                        )
                    )
                    if (contentError.isNotEmpty()) {
                        Text(
                            text = contentError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Category selector
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = DarkGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                label = {
                                    Text(
                                        text = category,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MediumGreen,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Error from ViewModel
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Post button
                    Button(
                        onClick = {
                            var valid = true
                            if (title.isBlank()) {
                                titleError = "Title is required"
                                valid = false
                            }
                            if (content.isBlank()) {
                                contentError = "Content is required"
                                valid = false
                            }
                            if (valid) {
                                announcementViewModel.postAnnouncement(
                                    title = title,
                                    content = content,
                                    category = selectedCategory,
                                    postedBy = sessionManager.getUsername()
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MediumGreen,
                            contentColor = Color.White
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Post Announcement",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // View announcements button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("announcements") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(PaleGreen, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.CheckCircle,
                            contentDescription = null,
                            tint = MediumGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "View All Announcements",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = DarkGreen
                        )
                        Text(
                            text = "See what students are reading",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }
                }
            }
        }
    }

    // Success dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                title = ""
                content = ""
                selectedCategory = "General"
            },
            icon = {
                Icon(
                    Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = MediumGreen,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = { Text("Announcement Posted!") },
            text = {
                Text("Your announcement has been posted to Firestore and is now visible to all students.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        title = ""
                        content = ""
                        selectedCategory = "General"
                    }
                ) {
                    Text("OK", color = MediumGreen)
                }
            }
        )
    }
}
