package com.example.smartcampuscompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf("") }
    var contentError by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val categories = listOf("General", "Academic", "Advisory", "Events", "Facilities")
    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    LaunchedEffect(postSuccess) {
        if (postSuccess) {
            showSuccessDialog = true
            announcementViewModel.clearPostSuccess()
            title = ""
            content = ""
            selectedCategory = "General"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Body
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(260.dp))
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column {
                    Text(
                        text = "Admin Panel",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Post Announcement card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            OutlinedTextField(
                                value = title,
                                onValueChange = { title = it; titleError = "" },
                                label = { Text("Announcement Title") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                isError = titleError.isNotEmpty(),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MediumGreen,
                                    focusedLabelColor = MediumGreen
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = content,
                                onValueChange = { content = it; contentError = "" },
                                label = { Text("Main Content") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp),
                                shape = RoundedCornerShape(14.dp),
                                isError = contentError.isNotEmpty(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MediumGreen,
                                    focusedLabelColor = MediumGreen
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ExposedDropdownMenuBox(
                                    expanded = categoryDropdownExpanded,
                                    onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    OutlinedTextField(
                                        value = selectedCategory,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Category") },
                                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                                        modifier = Modifier.menuAnchor(),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MediumGreen,
                                            focusedLabelColor = MediumGreen
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = categoryDropdownExpanded,
                                        onDismissRequest = { categoryDropdownExpanded = false }
                                    ) {
                                        categories.forEach { category ->
                                            DropdownMenuItem(
                                                text = { Text(category) },
                                                onClick = {
                                                    selectedCategory = category
                                                    categoryDropdownExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.width(12.dp))

                                Button(
                                    onClick = {
                                        var valid = true
                                        if (title.isBlank()) { titleError = "Required"; valid = false }
                                        if (content.isBlank()) { contentError = "Required"; valid = false }
                                        if (valid) {
                                            announcementViewModel.postAnnouncement(
                                                title = title,
                                                content = content,
                                                category = selectedCategory,
                                                postedBy = sessionManager.getUsername()
                                            )
                                        }
                                    },
                                    modifier = Modifier.height(56.dp).weight(0.7f),
                                    shape = RoundedCornerShape(14.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MediumGreen),
                                    enabled = !isLoading
                                ) {
                                    if (isLoading) {
                                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    } else {
                                        Icon(Icons.Rounded.Send, null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Post")
                                    }
                                }
                            }

                            if (errorMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(errorMessage!!, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Column {
                    Text(
                        text = "Quick Management",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        AdminQuickCard(
                            modifier = Modifier.weight(1f),
                            title = "Announcements",
                            subtitle = "Edit or Delete",
                            icon = Icons.Rounded.Campaign,
                            onClick = { navController.navigate("admin_announcements") }
                        )
                        AdminQuickCard(
                            modifier = Modifier.weight(1f),
                            title = "App Settings",
                            subtitle = "Configure system",
                            icon = Icons.Rounded.Settings,
                            onClick = { navController.navigate("settings") }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Sophisticated Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(
                    gradient,
                    shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                )
        ) {
            // Background Decoration
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .offset(x = (-40).dp, y = (-40).dp)
                    .background(Color(0x0DFFFFFF), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 20.dp, y = 20.dp)
                    .border(15.dp, Color(0x0DFFFFFF), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(56.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            authUtils.signOut()
                            sessionManager.clearSession()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0x1AFFFFFF))
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.Logout, "Logout", tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                    
                    Surface(
                        color = Color(0x26FFFFFF),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "SYSTEM ADMINISTRATOR",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, letterSpacing = 1.2.sp),
                            color = Color.White
                        )
                    }

                    IconButton(
                        onClick = { navController.navigate("profile") },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0x1AFFFFFF))
                    ) {
                        Icon(Icons.Rounded.Person, "Profile", tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.padding(bottom = 36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PaleGreen, RoundedCornerShape(22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, fontSize = 28.sp),
                                color = MediumGreen
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = "Admin",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.5).sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = sessionManager.getUsername(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xCCFFFFFF)
                        )
                    }
                }
            }
        }
    }

    // Success dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = { Icon(Icons.Rounded.CheckCircle, null, tint = MediumGreen, modifier = Modifier.size(36.dp)) },
            title = { Text("Broadcast Successful") },
            text = { Text("The announcement has been pushed to all campus companion apps.") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) { Text("Dismiss", color = MediumGreen) }
            }
        )
    }
}

@Composable
private fun AdminQuickCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(115.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(PaleGreen.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = MediumGreen, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
