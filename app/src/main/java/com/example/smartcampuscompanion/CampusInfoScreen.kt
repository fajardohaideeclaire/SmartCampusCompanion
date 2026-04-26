package com.example.smartcampuscompanion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcampuscompanion.ui.theme.DarkGreen
import com.example.smartcampuscompanion.ui.theme.MediumGreen
import com.example.smartcampuscompanion.ui.theme.PaleGreen

data class Program(
    val title: String,
    val majors: List<String> = emptyList()
)

data class Department(
    val code: String,
    val name: String,
    val email: String,
    val building: String,
    val phone: String,
    val head: String,
    val programs: List<Program>
)

@Composable
fun CampusInfoScreen(navController: NavHostController) {
    val departments = listOf(
        Department(
            code = "CAS",
            name = "College of Arts and Sciences",
            email = "cas@campus.edu",
            building = "Block A, Room 101",
            phone = "+63 2 8888 0001",
            head = "Dr. Jinky C. Malabanan",
            programs = listOf(
                Program("Bachelor of Science in Psychology")
            )
        ),
        Department(
            code = "CBAA",
            name = "College of Business, Accountancy and Administration",
            email = "cbaa@campus.edu",
            building = "Block B, Room 201",
            phone = "+63 2 8888 0002",
            head = "Dr. Rem Bucal",
            programs = listOf(
                Program("Bachelor of Science in Accountancy"),
                Program(
                    title = "Bachelor of Science in Business Administration",
                    majors = listOf(
                        "Major in Financial Management",
                        "Major in Marketing Management"
                    )
                )
            )
        ),
        Department(
            code = "CCS",
            name = "College of Computing Studies",
            email = "ccs@campus.edu",
            building = "Block C, Room 301",
            phone = "+63 2 8888 0003",
            head = "Dr. Gima B. Montecillo",
            programs = listOf(
                Program("Bachelor of Science in Computer Science"),
                Program("Bachelor of Science in Information Technology")
            )
        ),
        Department(
            code = "COED",
            name = "College of Education",
            email = "coed@campus.edu",
            building = "Block D, Room 401",
            phone = "+63 2 8888 0004",
            head = "Dr. Nikki Crystel A. Elic",
            programs = listOf(
                Program("Bachelor of Elementary Education"),
                Program(
                    title = "Bachelor of Secondary Education",
                    majors = listOf(
                        "Major in English",
                        "Major in Filipino",
                        "Major in Mathematics",
                        "Major in Social Sciences"
                    )
                )
            )
        ),
        Department(
            code = "COE",
            name = "College of Engineering",
            email = "coe@campus.edu",
            building = "Block E, Room 501",
            phone = "+63 2 8888 0005",
            head = "Dr. Rizal M. Mosquera",
            programs = listOf(
                Program("Bachelor of Science in Computer Engineering"),
                Program("Bachelor of Science in Electronics Engineering"),
                Program("Bachelor of Science in Industrial Engineering")
            )
        ),
        Department(
            code = "CHAS",
            name = "College of Health and Allied Sciences",
            email = "chas@campus.edu",
            building = "Block F, Room 601",
            phone = "+63 2 8888 0006",
            head = "Dr. Emelyn A. Buenaseda",
            programs = listOf(
                Program("Bachelor of Science in Nursing")
            )
        ),
        Department(
            code = "GS",
            name = "Graduate School",
            email = "gradschool@campus.edu",
            building = "--------",
            phone = "+63 2 8888 0007",
            head = "---------",
            programs = listOf(
                Program(
                    title = "Master of Arts in Education",
                    majors = listOf("Major in Administration and Supervision")
                ),
                Program("Master of Arts in Psychology"),
                Program("Master in Business Administration")
            )
        )
    )

    val gradient = Brush.linearGradient(colors = listOf(DarkGreen, MediumGreen))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Body - Placed first so it scrolls UNDER the header
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 196.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Colleges & Programs",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            items(departments) { dept ->
                DepartmentCard(dept)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Campus Services",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            item {
                FacilityInfoCard(
                    title = "Library",
                    detail = "Open Mon–Sat, 7AM–8PM · Block G",
                    icon = Icons.AutoMirrored.Rounded.LibraryBooks,
                    color = Color(0xFFE3F2FD),
                    iconColor = Color(0xFF2196F3)
                )
            }
            item {
                FacilityInfoCard(
                    title = "Clinic",
                    detail = "Open Mon–Fri, 8AM–5PM · Block H · +63 2 8888 0099",
                    icon = Icons.Rounded.LocalHospital,
                    color = Color(0xFFE8F5E9),
                    iconColor = MediumGreen
                )
            }
            item {
                FacilityInfoCard(
                    title = "Registrar",
                    detail = "Open Mon–Fri, 8AM–5PM · Admin Building · registrar@campus.edu",
                    icon = Icons.AutoMirrored.Rounded.Assignment,
                    color = Color(0xFFFFF3E0),
                    iconColor = Color(0xFFFF9800)
                )
            }
        }

        // Sophisticated Header - Placed last so it stays on top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    gradient,
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
        ) {
            // Background Decoration
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = (-30).dp, y = (-30).dp)
                    .background(Color(0x0DFFFFFF), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 20.dp, y = 20.dp)
                    .border(15.dp, Color(0x0DFFFFFF), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0x1AFFFFFF)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = "Campus Information",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.5).sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = "Academic Colleges & Facilities",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xCCFFFFFF)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DepartmentCard(dept: Department) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // College code badge
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(color = PaleGreen, shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dept.code,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MediumGreen
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dept.name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = dept.building,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp
                    else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 20.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Contact Information",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MediumGreen
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoRow(icon = Icons.Rounded.Email, text = dept.email)
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoRow(icon = Icons.Rounded.Phone, text = dept.phone)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "College Dean",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = dept.head,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Programs list
                    Text(
                        text = "Programs Offered",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MediumGreen
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    dept.programs.forEach { program ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = program.title,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (program.majors.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    program.majors.forEach { major ->
                                        Text(
                                            text = "• $major",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun FacilityInfoCard(
    title: String,
    detail: String,
    icon: ImageVector,
    color: Color,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = detail,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
