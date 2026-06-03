package com.app.calllogs.ui.leads

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.CallHistoryActivity
import com.app.calllogs.R
import com.app.calllogs.di.data.Activity
import com.app.calllogs.di.data.Lead
import com.app.calllogs.ui.contact.AvatarLead
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.ui.settings.AccompanistWebView
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.DarkBackground
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.openDialer
import com.app.calllogs.utils.relatedToModule
import com.app.calllogs.utils.relatedToRecordId
import com.app.calllogs.utils.relatedToZohoId
import com.app.calllogs.utils.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLeadScreen(
    navController: NavController,
    leadId: String,
    viewModel: EditLeadViewModel = hiltViewModel(),
) {

    val lead by viewModel.lead.collectAsState()
    val isLoading = lead == null

    val context = LocalContext.current

    // Fetch lead details if not already available
    LaunchedEffect(leadId) {
        viewModel.getLeadDetails(leadId)
    }
    var isWebOpen by remember { mutableStateOf(false) }
    var webUrl by remember { mutableStateOf("") }
    if (isWebOpen) {
        AccompanistWebView(webUrl)
    } else {
        if (isLoading) {
//        CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            Box(
                modifier = Modifier.fillMaxSize(), // Makes the Box take up the full available space
                contentAlignment = Alignment.Center // Centers the content (the indicator) inside the Box
            ) {
                CircularProgressIndicator()
            }
        } else {
            Scaffold(
                containerColor = DarkBackground,
                topBar = {
                    TopAppBar(
                        title = { Text("Lead Details", style = typography.headlineMedium) },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                openDialer(context,lead?.lead?.dynamicFields?.phone?:"03001234567")
                            }) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = "Call",
                                    tint = Color.White
                                )
                            }

                            IconButton(onClick = {
                                navController.navigate("EditLeadDynamicScreen/$leadId") {
                                    popUpTo("EditLeadDynamicScreen") {
                                        this.inclusive = true
                                    }
                                }
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color.White
                                )
                            }
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = CardColorD,
                            titleContentColor = Color.White
                        )
                    )
                }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 82.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(3.dp))
                    // Profile Image
                    AvatarLead(
                        lead?.lead?.dynamicFields?.fullName ?: lead?.lead?.dynamicFields?.lastName
                        ?: "lead"
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Name
                    Text(
                        text = lead?.lead?.dynamicFields?.fullName
                            ?: lead?.lead?.dynamicFields?.lastName ?: "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Tags Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Tag(
                            text = "NEW LEAD",
                            color = Color(0xFFECFDF5),
                            textColor = Color(0xFF047857)
                        )
                        Tag(
                            text = "HIGH PRIORITY",
                            color = Color(0xFFFEE2E2),
                            textColor = Color(0xFFB91C1C)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action Buttons Row
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActionButton(icon = R.drawable.outline_call_log_24, label = "Calls") {
                            relatedToModule = "Leads"
                            relatedToRecordId = lead?.lead?._id?:""
                            relatedToZohoId = lead?.lead?.zohoLeadId?:""
                            navController.navigate(Routes.CallListScreen) {
                                popUpTo(Routes.CallListScreen) {
                                    inclusive = true
                                }
                            }
                        }
                        ActionButton(icon = R.drawable.baseline_task_24, label = "Tasks") {
                            relatedToModule = "Leads"
                            relatedToRecordId = lead?.lead?._id?:""
                            relatedToZohoId = lead?.lead?.zohoLeadId?:""
                            navController.navigate(Routes.TaskListScreen) {
                                popUpTo(Routes.TaskListScreen) {
                                    inclusive = true
                                }
                            }
                        }
                        ActionButton(icon = R.drawable.outline_meeting_room_24, label = "Meeting") {
                            relatedToModule = "Leads"
                            relatedToRecordId = lead?.lead?._id?:""
                            relatedToZohoId = lead?.lead?.zohoLeadId?:""
                            navController.navigate(Routes.MeetingistScreen) {
                                popUpTo(Routes.MeetingistScreen) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact Information Section
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Contact Information",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColor
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Company",
                            value = lead?.lead?.dynamicFields?.company ?: "",
                            icon = Icons.Outlined.MailOutline
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "First Name",
                            value = lead?.lead?.dynamicFields?.firstName ?: "",
                            icon = Icons.Outlined.MailOutline
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Last Name",
                            value = lead?.lead?.dynamicFields?.lastName ?: "",
                            icon = Icons.Outlined.MailOutline
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Work Email",
                            value = lead?.lead?.dynamicFields?.email ?: "",
                            icon = Icons.Outlined.MailOutline
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Mobile",
                            value = lead?.lead?.dynamicFields?.phone ?: "",
                            icon = Icons.Outlined.Phone
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Fax",
                            value = lead?.lead?.dynamicFields?.fax ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Industry",
                            value = lead?.lead?.dynamicFields?.industry ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Website",
                            value = lead?.lead?.dynamicFields?.website ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "No. of Employees",
                            value = lead?.lead?.dynamicFields?.noOfEmployees.toString() ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Annual Revenue",
                            value = lead?.lead?.dynamicFields?.annualRevenue ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Description",
                            value = lead?.lead?.dynamicFields?.description ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Skype ID",
                            value = lead?.lead?.dynamicFields?.skypeId ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Email Opt Out",
                            value = lead?.lead?.dynamicFields?.emailOptOut.toString() ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Salutation",
                            value = lead?.lead?.dynamicFields?.salutation ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Secondary Email",
                            value = lead?.lead?.dynamicFields?.secondaryEmail ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Twitter",
                            value = lead?.lead?.dynamicFields?.twitter ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        Text(
                            text = "Address Information",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColor
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Street",
                            value = lead?.lead?.dynamicFields?.street ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "City",
                            value = lead?.lead?.dynamicFields?.city ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "State",
                            value = lead?.lead?.dynamicFields?.state ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Zip",
                            value = lead?.lead?.dynamicFields?.zipCode ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Country",
                            value = lead?.lead?.dynamicFields?.country ?: "",
                            icon = Icons.Outlined.AccountBox
                        )
                        Text(
                            text = "Lead Summary",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColor
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Lead Status",
                            value = lead?.lead?.dynamicFields?.leadStatus ?: "--None--",
                            icon = Icons.Outlined.AccountBox
                        )
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ContactRow(
                            label = "Lead Source",
                            value = lead?.lead?.dynamicFields?.leadSource ?: "--None--",
                            icon = Icons.Outlined.AccountBox
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Activity History",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextColor
                        )
                        // Usually a filter icon or "See All" text goes here, keeping simple per image
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Timeline Items
                    Column {
                        TimelineItem(
                            title = "Outgoing Call",
                            time = "2 hours ago",
                            description = "Discussed premium package",
                            icon = Icons.Outlined.Call,
                            isFirst = true,
                            onCallHistory = {
                                val intent = Intent(context, CallHistoryActivity::class.java)
                                intent.putExtra("cid", lead?.lead?._id)
                                context.startActivity(intent)
                            }
                        )
//                    TimelineItem(
//                        title = "Lead Created",
//                        time = "Yesterday",
//                        description = "From LinkedIn Q4 Growth campaign",
//                        icon = Icons.Outlined.Add,
//                        isFirst = false
//                    )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate("ConvertLeadScreen/$leadId") {
                                popUpTo("ConvertLeadScreen") {
                                    this.inclusive = true
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = CardColorD)
                    ) { Text("Convert Lead", style = typography.bodySmall, color = Color.White) }
                }
            }
        }
    }
}

@Composable
fun Tag(text: String, color: Color, textColor: Color) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
fun ActionButton(icon: Int, label: String,onItemClick : ()->Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp).clickable(onClick = onItemClick)
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFF3F4F6),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painterResource(icon),
//                    imageVector = icon,
                    contentDescription = label,
                    tint = Color(0xFF4B5563),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextColor
        )
    }
}

@Composable
fun ContactRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        }
        // Copy icon often appears here, omitted to keep strictly to description
    }
}

@Composable
fun TimelineItem(
    title: String,
    time: String,
    description: String,
    icon: ImageVector,
    isFirst: Boolean,
    onCallHistory : ()->Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onCallHistory)
    ) {
        // Line
        Box {
            if (!isFirst) {
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(Color(0xFFE5E7EB))
                        .align(Alignment.Center)
                )
            }
            Surface(
                shape = CircleShape,
                color = CardColorD, // Light indigo
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(10.dp)
                        .padding(5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF4B5563),
                lineHeight = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun LeadDetailsPreview() {
    MaterialTheme {
        EditLeadScreen(rememberNavController(), "")
    }
}

@Composable
fun AvatarLead(seed: String) {
    Surface(
        shape = CircleShape,
        color = Color(0xFF1B2F43),
        modifier = Modifier.size(70.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(seed.take(2), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Composable
fun LeadInfo(lead: Lead?) {
    Column {
        Text("Lead Details", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Profile Image
            Surface(
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            ) {
                Text(
                    text = (lead?.dynamicFields?.firstName?.take(1) + lead?.dynamicFields?.lastName?.take(
                        1
                    )),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Lead Name and Status
            Column {
                Text(
                    "${lead?.dynamicFields?.firstName} ${lead?.dynamicFields?.lastName}",
                    style = MaterialTheme.typography.headlineMedium
                )
                Row {
                    Text(
                        text = lead?.dynamicFields?.leadStatus.orEmpty(),
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = lead?.dynamicFields?.priority.orEmpty(),
                        color = Color(0xFFFF8C00), // Orange color for high priority
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun ContactInfo(lead: Lead?) {
    Column {
        Text("Contact Information", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Email, contentDescription = "Email")
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.email.orEmpty())
        }

        // Phone
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Phone, contentDescription = "Phone")
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.phone.orEmpty())
        }

        // Lead Source
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Share, contentDescription = "Lead Source")
            Spacer(modifier = Modifier.width(8.dp))
            Text(lead?.dynamicFields?.leadSource.orEmpty())
        }
    }
}

@Composable
fun ActivityHistory(activityHistory: List<Activity>) {
    Column {
        Text("Activity History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        // Loop through activity history
        activityHistory.forEach { activity ->
            Row {
                Icon(Icons.Default.Info, contentDescription = "Activity")
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(activity.activityType, style = MaterialTheme.typography.bodyMedium)
                    Text(activity.description, style = MaterialTheme.typography.bodyMedium)
                    Text(activity.timestamp, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Add Note Button
        TextButton(onClick = { /* Open add note functionality */ }) {
            Text("Add Note")
        }
    }
}

fun openInCustomTab(context: Context, url: String) {
//    val intent = androidx.browser.customtabs.CustomTabsIntent.Builder().build()
//    intent.launchUrl(context, android.net.Uri.parse(url))
}