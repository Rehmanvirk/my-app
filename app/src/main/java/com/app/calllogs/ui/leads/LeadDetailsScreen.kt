package com.app.calllogs.ui.leads

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.calllogs.di.data.Activity
import com.app.calllogs.di.data.Lead
import com.app.calllogs.utils.ButtonBlue
import com.app.calllogs.utils.DarkBackground
import com.app.calllogs.utils.OrangePriority
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.typography

@Composable
fun LeadDetailsScreenRoute(navController: NavController,leadId: String, viewModel: EditLeadViewModel = hiltViewModel()) {
    val lead by viewModel.lead.collectAsState()

    // Fetch data if lead is not available
    LaunchedEffect(leadId) {
        viewModel.getLeadDetails(leadId)
    }

    if (lead == null) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        LeadDetailsScreen(lead = lead?.lead, onBack = { navController.popBackStack()})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadDetailsScreen(lead: Lead?, onBack: () -> Unit) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Lead Details", style = typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextColor)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle Call */ }) {
                        Icon(Icons.Default.Phone, contentDescription = "Call", tint = TextColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextColor
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { LeadInfo1(lead) }
            item { ContactInformation(lead) }
            item { ActivityHistory1(lead?.dynamicFields?.activityHistory) }
            item { AddNoteSection() }
            item {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue)
                ) { Text("Save Changes", style = typography.bodySmall) }
            }
            item { Spacer(Modifier.height(30.dp)) }
        }
    }
}

@Composable
fun LeadInfo1(lead: Lead?) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight() ,horizontalAlignment = Alignment.CenterHorizontally) {
        // Profile image (using initials from first and last name)
//        Surface(
//            shape = CircleShape,
//            color = Color.Gray,
//            modifier = Modifier.size(60.dp)
//        ) {
//            Text(
//                text = "${lead?.dynamicFields?.First_Name?.first()}${lead?.dynamicFields?.Last_Name?.first()}",
//                style = typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                color = Color.White
//            )
//        }
        Surface(
            shape = CircleShape,
            color = Color(0xFF1B2F43),
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(lead?.dynamicFields?.fullName.orEmpty().take(2), color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))

        // Lead Name and Status
        Column(modifier = Modifier.wrapContentSize()) {
            Text(lead?.dynamicFields?.firstName + " " + lead?.dynamicFields?.lastName, style = typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text("NEW LEAD", style = typography.bodySmall.copy(color = ButtonBlue))
                Spacer(modifier = Modifier.width(8.dp))
                Text("HIGH PRIORITY", style = typography.bodySmall.copy(color = OrangePriority))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun ContactInformation(lead: Lead?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("CONTACT INFORMATION", style = typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Email, contentDescription = "Email", tint = TextColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.email.orEmpty(), style = typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Phone
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Phone, contentDescription = "Phone", tint = TextColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.phone.orEmpty(), style = typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lead Source
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Share, contentDescription = "Lead Source", tint = TextColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.leadSource.orEmpty(), style = typography.bodySmall)
        }
    }
}

@Composable
fun ActivityHistory1(activityHistory: List<Activity>?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("ACTIVITY HISTORY", style = typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        activityHistory?.forEach { activity ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = "Activity", tint = TextColor)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(activity.activityType, style = typography.bodySmall)
                    Text(activity.description, style = typography.bodySmall)
                    Text(activity.timestamp, style = typography.bodySmall)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Add Note Button
        TextButton(onClick = { /* Handle adding note */ }) {
            Text("ADD NOTE", style = typography.bodySmall.copy(color = ButtonBlue))
        }
    }
}

@Composable
fun AddNoteSection() {
    TextButton(onClick = { /* Handle adding a note */ }) {
        Text("Add Note", style = typography.bodySmall.copy(color = ButtonBlue))
    }
}

