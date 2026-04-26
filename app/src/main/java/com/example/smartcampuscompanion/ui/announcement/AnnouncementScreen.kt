package com.example.smartcampuscompanion.ui.announcement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
// Added Imports
import androidx.compose.material3.LinearProgressIndicator
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AnnouncementScreen(viewModel: AnnouncementViewModel, navController: NavHostController) {
    val announcements by viewModel.announcements.collectAsState()
    // Added isLoading state
    val isLoading by viewModel.isLoading.collectAsState()

    val unread = announcements.filter { !it.isRead }
    val read = announcements.filter { it.isRead }

    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8F7))
    ) {
        // ── Header ───────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(top = 52.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0x1AFFFFFF), CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Announcements",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = if (unread.isEmpty()) "All caught up!"
                        else "${unread.size} unread message${if (unread.size > 1) "s" else ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xB3FFFFFF)
                    )
                }
            }
        }

        // Added Loading Indicator Logic
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MediumGreen,
                trackColor = Color(0xFFCCCCCC)
            )
        }

        // --- Body ---
        if (announcements.isEmpty() && !isLoading) {
            // Empty state (only shows if not loading)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(PaleGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = MediumGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "No Announcements Yet",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Check back later for updates from your campus.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF888888)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (unread.isNotEmpty()) {
                    item {
                        SectionLabel(text = "New", badge = unread.size)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(unread, key = { it.firestoreId.ifEmpty { it.title } }) { item ->
                        AnnouncementCard(
                            title = item.title,
                            content = item.content,
                            date = item.date,
                            category = item.category,
                            isRead = false,
                            onMarkAsRead = { viewModel.markAsRead(item) }
                        )
                    }
                }

                if (read.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        SectionLabel(text = "Earlier")
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    items(read, key = { it.firestoreId.ifEmpty { it.title } }) { item ->
                        AnnouncementCard(
                            title = item.title,
                            content = item.content,
                            date = item.date,
                            category = item.category,
                            isRead = true,
                            onMarkAsRead = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String, badge: Int? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = DarkGreen
        )
        if (badge != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .background(MediumGreen, RoundedCornerShape(50.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badge.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun AnnouncementCard(
    title: String,
    content: String,
    date: String,
    category: String,
    isRead: Boolean,
    onMarkAsRead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRead) Color(0xFFF9F9F9) else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isRead) 0.dp else 2.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            if (isRead) Color(0xFFEEEEEE) else PaleGreen,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = if (isRead) Color(0xFF888888) else MediumGreen
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!isRead) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .background(MediumGreen, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF888888)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = if (isRead) FontWeight.Normal else FontWeight.SemiBold
                ),
                color = if (isRead) Color(0xFF666666) else DarkGreen
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = if (isRead) Color(0xFF999999) else Color(0xFF444444)
            )

            if (!isRead) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onMarkAsRead,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MediumGreen,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = "Mark as Read",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}