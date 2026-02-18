package com.example.smartcampuscompanion.ui.announcement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState

import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@Composable
fun AnnouncementScreen(viewModel: AnnouncementViewModel) {

    val announcements by viewModel.announcements.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        items(announcements) { item ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = item.content)

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!item.isRead) {
                        androidx.compose.material3.Button(
                            onClick = {
                                viewModel.markAsRead(item)
                            }
                        ) {
                            Text("Mark as Read")
                        }
                    }

                }
            }
        }
    }
}
