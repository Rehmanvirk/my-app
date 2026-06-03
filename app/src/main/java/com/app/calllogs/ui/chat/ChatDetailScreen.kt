package com.app.calllogs.ui.chat

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.calllogs.di.data.Messages
import com.app.calllogs.utils.DarkBackground
import com.app.calllogs.utils.TextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController, parentPadding: PaddingValues,
    conversationId: String,
    vm: ChatViewModel = hiltViewModel()
) {
    val messages = rememberChatMessages()
    var messageText by remember { mutableStateOf("") }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route

    val showBottomBar = route !in listOf("chatDetail/{chatId}") // add other full-screen routes
    val state by vm.state.collectAsState()

    val convsersation = conversationId.split("-")

    val id  by remember { mutableStateOf(convsersation[0])}
    val name  by remember { mutableStateOf(convsersation[1])}
    val status  by remember { mutableStateOf(convsersation[1])}
    LaunchedEffect(conversationId) {
        vm.getMessagesList(id)
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    if (!state.isLoading){
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                scope.launch {
                    // Animate scroll to the last index of the list
                    listState.animateScrollToItem(index = messages.lastIndex)
                }
            }
        }
    }
    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Spacer(modifier = Modifier.width(0.dp)) // Align left
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981)) // Green Online
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Online",
                                fontSize = 12.sp,
                                color = Color(0xFF10B981)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Call, contentDescription = "Call")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    navigationIconContentColor = TextColor,
                    titleContentColor = TextColor
                )
            )
        },
        bottomBar = {
            // Message Input Area
            Surface(
                shadowElevation = 4.dp,
                tonalElevation = 2.dp,
                color = DarkBackground,
                modifier = Modifier.padding(bottom = parentPadding.calculateBottomPadding())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Outlined.MoreVert,
                            contentDescription = "Attach",
                            tint = Color(0xFF6B7280)
                        )
                    }
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Message...") },
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF3F4F6),
                            unfocusedContainerColor = Color(0xFFF3F4F6),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        vm.sendMessage(state.allMessages[0].senderId,messageText, conversationId = id)
                        messageText = ""
                    }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFF2563EB), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.Send,
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        // Chat Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(bottom = 60.dp)
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Group messages visually or just list them
                items(state.allMessages.filter { !it.content.isNullOrEmpty() }) { message ->
                    if (message.content!=null)
                        ChatBubble(message = message,vm.getId())
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Messages,currentUser : String) {
    val isMe = message.senderId == currentUser
    if (message.content!=null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
        ) {
            Column(
                horizontalAlignment = if (isMe) Alignment.End else Alignment.Start,
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                // Time Label (Optional, above bubble)
                if (!isMe) {
                    // Usually received messages don't show time above in simple UIs,
                    // but let's keep it clean like the description
                }

                // Bubble Shape
                val shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isMe) 16.dp else 0.dp,
                    bottomEnd = if (isMe) 0.dp else 16.dp
                )

                Surface(
                    shape = shape,
                    color = if (isMe) Color(0xFF2563EB) else Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = message.content,
                            color = if (isMe) Color.White else Color(0xFF1F2937),
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Timestamp below bubble
                Text(
                    text = message.timestamp,
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}

// Data Class
data class ChatMessage(
    val id: String,
    val text: String,
    val time: String,
    val isMe: Boolean
)

// Data Generation matching the description
fun rememberChatMessages(): List<ChatMessage> {
    return listOf(
        ChatMessage(
            "1",
            "Hey, did you see the update on the Miller deal?",
            "10:05 AM",
            isMe = false
        ),
        ChatMessage(
            "2",
            "Yes, they increased the budget by 15%.",
            "10:06 AM",
            isMe = true
        ),
        ChatMessage(
            "3",
            "That's great news! I need to send the revised contract. Do you have the latest template?",
            "10:08 AM",
            isMe = false
        ),
        ChatMessage(
            "4",
            "I'll handle that right now and CC you.",
            "10:10 AM",
            isMe = true
        )
    )
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun ChatDetailPreview() {
//    ChatDetailScreen()
}