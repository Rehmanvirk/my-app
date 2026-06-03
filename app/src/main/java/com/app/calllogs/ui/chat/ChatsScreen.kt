package com.app.calllogs.ui.chat

import android.R.attr.maxLines
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.di.data.Conversations
import com.app.calllogs.ui.deals.DealsViewModel
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.bg
import com.app.calllogs.utils.formatDateTime

@Composable
fun ChatsScreen(
    navController: NavController,
    contentPadding: PaddingValues,
    vm: ChatViewModel = hiltViewModel()
) {
    // Data matching the description
    val chats = listOf(
        ChatItem("Sarah Jenkins", "Hey, did you see the new lead for...", "14:20", isActive = true),
        ChatItem("Marcus Thorne", "The contract has been signed and...", "10:45", isActive = false),
        ChatItem("Elena Rodriguez", "Let's sync up about the open hous...", "Wed", isActive = true),
        ChatItem("David Kim", "Thanks for the update. I'll let the...", "Tue", isActive = false),
        ChatItem("Lisa Wang", "The inspection report just came...", "Oct 12", isActive = true)
    )

    val state by vm.state.collectAsState()
    if (state.isLoading) {

    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = contentPadding)
                .background(Color.White) // Assuming white background based on typical clean chat apps
                .padding(8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chats",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "New Chat",
                        tint = TextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                placeholder = {
                    Text(
                        text = "Search agents...",
                        color = Color(0xFF9CA3AF),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF9CA3AF)
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CardColor,
                    unfocusedContainerColor = CardColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Messages Header
            Text(
                text = "RECENT MESSAGES",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chat List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.allChats) { chat ->
                    ChatRow(chat = chat, onClick = {
//                        navController.navigate(Routes.ChatDetailsScreen) {
//                            popUpTo(Routes.ChatDetailsScreen) {
//                                inclusive = true
//                            }
//                        }
                        val idToPass = chat._id
                        val nameToPass = chat.lastMessage.sender.name
                        val status = chat.lastMessage.status
                        navController.navigate("Routes.ChatDetailsScreen/$idToPass-$nameToPass-$status") {
                            popUpTo("Routes.ChatDetailsScreen"){
                                this.inclusive = true
                            }
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun ChatRow(chat: Conversations, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() })
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar Container
        Box {
            // Avatar (Color placeholder)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(CardColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.lastMessage.sender.name.first().toString(),
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            // Online Status Indicator
            if (chat.lastMessage.sender.status=="active") {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.BottomEnd)
                        .padding(2.dp) // Border for the status dot
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFF10B981)) // Green active color
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = chat.lastMessage.sender.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Text(
                    text = formatDateTime(chat.lastMessage.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF9CA3AF)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = chat.lastMessage.text?:chat.lastMessage.file?.fileName?:"",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9CA3AF),
                maxLines = 1
            )
        }
    }
}


// Data Class
data class ChatItem(
    val name: String,
    val lastMessage: String,
    val time: String,
    val isActive: Boolean
)

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun ChatsScreenPreview() {
    MaterialTheme {
//        ChatsScreen(rememberNavController(), 0)
    }
}