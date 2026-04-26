package com.example.smartcampuscompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Shield
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

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    val email = sessionManager.getUsername()
    val role = sessionManager.getRole()

    // Derive display name from email (everything before @)
    val displayName = email.substringBefore("@")
        .replaceFirstChar { it.uppercase() }

    val roleLabel = when (role) {
        UserRole.ADMIN   -> "Administrator"
        UserRole.STUDENT -> "Student"
    }

    val roleColor = when (role) {
        UserRole.ADMIN   -> Color(0xFF1565C0)
        UserRole.STUDENT -> MediumGreen
    }

    val roleBgColor = when (role) {
        UserRole.ADMIN   -> Color(0xFFE3F2FD)
        UserRole.STUDENT -> PaleGreen
    }

    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Column(
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
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Your account information",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xB3FFFFFF)
                    )
                }
            }
        }

        // Body
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(PaleGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayName.take(1).uppercase(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    ),
                    color = MediumGreen
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display name
            Text(
                text = displayName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Role badge
            Box(
                modifier = Modifier
                    .background(roleBgColor, RoundedCornerShape(50.dp))
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = roleLabel,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = roleColor
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Account Details",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = DarkGreen
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileInfoRow(
                        icon = Icons.Rounded.Person,
                        label = "Full Name",
                        value = displayName
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    ProfileInfoRow(
                        icon = Icons.Rounded.Email,
                        label = "Email Address",
                        value = email
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    ProfileInfoRow(
                        icon = Icons.Rounded.Shield,
                        label = "Role",
                        value = roleLabel
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(PaleGreen, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MediumGreen,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

