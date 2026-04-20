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
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    navController: NavHostController,
    announcementViewModel: AnnouncementViewModel
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val authUtils = remember { AuthUtils() }
    val username = sessionManager.getUsername()
    val role = sessionManager.getRole()

    val announcements by announcementViewModel.announcements.collectAsState()
    val isLoading by announcementViewModel.isLoading.collectAsState()
    val unreadCount = announcements.count { !it.isRead }

    val greeting = remember {
        when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 0..11  -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else      -> "Good Evening"
        }
    }

    val today = remember {
        SimpleDateFormat("EEEE, MMM d", Locale.getDefault()).format(Date())
    }

    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

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
                Text(
                    text = today,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.3.sp
                    ),
                    color = Color(0xB3FFFFFF)
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.navigate("announcements") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.Notifications,
                        contentDescription = "Notifications",
                        tint = Color(0xCCFFFFFF),
                        modifier = Modifier.size(22.dp)
                    )
                    if (unreadCount > 0) {
                        BadgeBox(
                            count = unreadCount,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0x26FFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = greeting,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xB3FFFFFF),
                textAlign = TextAlign.Center
            )
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        // Loading indicator for Firestore sync
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MediumGreen,
                trackColor = Color(0xFFCCCCCC)
            )
        }

        // Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Quick Access",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = DarkGreen,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            NavRowCard(
                icon = Icons.Rounded.LocationOn,
                title = "Campus Information",
                subtitle = "Colleges, departments & facilities",
                onClick = { navController.navigate("campus") }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NavTileCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Rounded.CheckCircle,
                    title = "Tasks",
                    subtitle = "My schedule",
                    onClick = { navController.navigate("tasks") }
                )
                Box(modifier = Modifier.weight(1f)) {
                    NavTileCard(
                        modifier = Modifier.fillMaxWidth(),
                        icon = Icons.Rounded.Notifications,
                        title = "Announcements",
                        subtitle = if (unreadCount > 0) "$unreadCount unread" else "All read",
                        subtitleColor = if (unreadCount > 0) MediumGreen else Color(0xFF9E9E9E),
                        onClick = { navController.navigate("announcements") }
                    )
                    if (unreadCount > 0) {
                        BadgeBox(
                            count = unreadCount,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "More",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = DarkGreen,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            NavRowCard(
                icon = Icons.Rounded.Settings,
                title = "Settings",
                subtitle = "Personalize your experience",
                onClick = { navController.navigate("settings") }
            )
        }
    }
}

@Composable
private fun BadgeBox(count: Int, modifier: Modifier = Modifier) {
    val label = if (count > 9) "9+" else count.toString()
    val width = if (count > 9) 26.dp else 18.dp
    Box(
        modifier = modifier
            .width(width)
            .height(18.dp)
            .background(Color(0xFFE53935), RoundedCornerShape(9.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NavRowCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(PaleGreen, RoundedCornerShape(13.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = MediumGreen, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF9E9E9E)
                )
            }
            Icon(
                Icons.Rounded.KeyboardArrowRight,
                null,
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun NavTileCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    subtitleColor: Color = Color(0xFF9E9E9E),
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(PaleGreen, RoundedCornerShape(13.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = MediumGreen, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = DarkGreen,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = subtitleColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

