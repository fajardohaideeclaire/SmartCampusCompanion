package com.example.smartcampuscompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val authUtils = remember { AuthUtils() }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        // Decorative circles
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = -60.dp, y = -60.dp)
                .background(Color(0x1AFFFFFF), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .background(Color(0x1AFFFFFF), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(Color(0x26FFFFFF), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Smart Campus",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold, fontSize = 30.sp
                ),
                color = Color.White
            )
            Text(
                text = "Companion",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold, fontSize = 30.sp
                ),
                color = Color(0xB3FFFFFF)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to your account to continue",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0x99FFFFFF),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = "" },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(Icons.Rounded.Email, null, tint = MediumGreen)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = DarkGreen,
                            unfocusedTextColor = DarkGreen,
                            focusedBorderColor = MediumGreen,
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedLabelColor = MediumGreen,
                            unfocusedLabelColor = Color(0xFF888888)
                        ),
                        isError = errorMessage.isNotEmpty()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = "" },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(Icons.Rounded.Lock, null, tint = MediumGreen)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Rounded.Visibility
                                    else Icons.Rounded.VisibilityOff,
                                    contentDescription = null,
                                    tint = MediumGreen
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = DarkGreen,
                            unfocusedTextColor = DarkGreen,
                            focusedBorderColor = MediumGreen,
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedLabelColor = MediumGreen,
                            unfocusedLabelColor = Color(0xFF888888)
                        ),
                        isError = errorMessage.isNotEmpty()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login button
                    Button(
                        onClick = {
                            when {
                                !authUtils.isEmailValid(email) -> {
                                    errorMessage = "Please enter a valid email"
                                }
                                !authUtils.isPasswordValid(password) -> {
                                    errorMessage = "Password must be at least 4 characters"
                                }
                                else -> {
                                    isLoading = true
                                    scope.launch {
                                        val role = authUtils.login(email, password)
                                        isLoading = false
                                        if (role != null) {
                                            sessionManager.saveSession(email, role)
                                            when (role) {
                                                UserRole.ADMIN ->
                                                    navController.navigate("admin") {
                                                        popUpTo("login") { inclusive = true }
                                                    }
                                                UserRole.STUDENT ->
                                                    navController.navigate("dashboard") {
                                                        popUpTo("login") { inclusive = true }
                                                    }
                                            }
                                        } else {
                                            errorMessage = "Invalid email or password"
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
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
                                text = "Login",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Student: student@campus.edu / 1234",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0x99FFFFFF),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Admin: admin@campus.edu / admin123",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0x99FFFFFF),
                textAlign = TextAlign.Center
            )
        }
    }
}

