package com.example.smartcampuscompanion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(top = 52.dp, bottom = 28.dp, start = 24.dp, end = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = -40.dp)
                    .background(color = Color(0x1AFFFFFF), shape = CircleShape)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(color = Color(0x1AFFFFFF), shape = CircleShape)
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Campus Information",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Tap a college to expand details",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xB3FFFFFF)
                    )
                }
            }
        }

        // Body
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Colleges & Programs",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(departments) { dept ->
                DepartmentCard(dept)
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Campus Facilities",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                FacilityInfoCard(
                    title = "Library",
                    detail = "Open Mon–Sat, 7AM–8PM · Block G"
                )
                Spacer(modifier = Modifier.height(8.dp))
                FacilityInfoCard(
                    title = "Clinic",
                    detail = "Open Mon–Fri, 8AM–5PM · Block H · +63 2 8888 0099"
                )
                Spacer(modifier = Modifier.height(8.dp))
                FacilityInfoCard(
                    title = "Registrar",
                    detail = "Open Mon–Fri, 8AM–5PM · Admin Building · registrar@campus.edu"
                )
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
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // College code badge
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = PaleGreen, shape = RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dept.code,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MediumGreen
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dept.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = DarkGreen
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF888888),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = dept.building,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF888888)
                        )
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp
                    else Icons.Rounded.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MediumGreen
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact info
                    InfoRow(icon = Icons.Rounded.Email, text = dept.email)
                    Spacer(modifier = Modifier.height(6.dp))
                    InfoRow(icon = Icons.Rounded.Phone, text = dept.phone)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Dean: ${dept.head}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = DarkGreen
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(10.dp))

                    // Programs list
                    Text(
                        text = "Programs Offered",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MediumGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    dept.programs.forEach { program ->
                        Row(
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.bodySmall,
                                color = MediumGreen,
                                modifier = Modifier.padding(end = 6.dp, top = 1.dp)
                            )
                            Column {
                                Text(
                                    text = program.title,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                    color = Color(0xFF333333)
                                )
                                program.majors.forEach { major ->
                                    Row(modifier = Modifier.padding(start = 8.dp, top = 2.dp)) {
                                        Text(
                                            text = "–",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF888888),
                                            modifier = Modifier.padding(end = 6.dp)
                                        )
                                        Text(
                                            text = major,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF666666)
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
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF888888),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF666666)
        )
    }
}

@Composable
fun FacilityInfoCard(title: String, detail: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PaleGreen),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = DarkGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detail,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF555555)
            )
        }
    }
}
