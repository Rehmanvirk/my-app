package com.app.calllogs.ui.deals

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.R
import com.app.calllogs.di.data.Activity
import com.app.calllogs.di.data.Lead
import com.app.calllogs.ui.contact.EditContactScreen
import com.app.calllogs.ui.contact.EditContactViewModel
import com.app.calllogs.ui.leads.ActionButton
import com.app.calllogs.ui.leads.ContactRow
import com.app.calllogs.ui.leads.EditLeadViewModel
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.bg
import com.app.calllogs.utils.openDialer
import com.app.calllogs.utils.relatedToModule
import com.app.calllogs.utils.relatedToRecordId
import com.app.calllogs.utils.relatedToZohoId
import com.app.calllogs.utils.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDealScreen(
    navController: NavController,
    contactId: String,
    viewModel: DealsViewModel = hiltViewModel(),
) {

    val deal by viewModel.deal.collectAsState()
    val isLoading = deal == null
    val context = LocalContext.current
    // Fetch lead details if not already available
    LaunchedEffect(contactId) {
        viewModel.getDealDetails(contactId)
    }

    if (isLoading) {
//        CircularProgressIndicator(modifier = Modifier.wrapContentSize())
        Box(
            modifier = Modifier.fillMaxSize(), // Makes the Box take up the full available space
            contentAlignment = Alignment.Center // Centers the content (the indicator) inside the Box
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar (Back arrow)
            TopAppBar(
                title = { Text("Deal Details", style = typography.headlineMedium)},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
//                        openDialer(context,deal?.deal.raw.cont ?:"03001234567")
                    }) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = "Call",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate("EditDealDynamicScreen/$contactId") {
                            popUpTo("EditDealDynamicScreen") {
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
                    navigationIconContentColor = Color.White
                )

            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(3.dp))
                // Profile Image
                AvatarLead(deal?.deal?.Deal_Name?:"")

                Spacer(modifier = Modifier.height(6.dp))

                // Name
                Text(
                    text = deal?.deal?.Deal_Name?:"",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Action Buttons Row
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
//                    ActionButton(icon = Icons.Outlined.Email, label = "Email")
                    ActionButton(icon = R.drawable.outline_call_log_24, label = "Calls") {
                        relatedToModule = "Deals"
                        relatedToRecordId = deal?.deal?._id?:""
                        relatedToZohoId = deal?.deal?.zohoDealId?:""
                        navController.navigate(Routes.CallListScreen) {
                            popUpTo(Routes.CallListScreen) {
                                inclusive = true
                            }
                        }
                    }
                    ActionButton(icon = R.drawable.baseline_task_24, label = "Tasks") {
                        relatedToModule = "Deals"
                        relatedToRecordId = deal?.deal?._id?:""
                        relatedToZohoId = deal?.deal?.zohoDealId?:""
                        navController.navigate(Routes.TaskListScreen) {
                            popUpTo(Routes.TaskListScreen) {
                                inclusive = true
                            }
                        }
                    }
                    ActionButton(icon = R.drawable.outline_meeting_room_24, label = "Meeting") {
                        relatedToModule = "Deals"
                        relatedToRecordId = deal?.deal?._id?:""
                        relatedToZohoId = deal?.deal?.zohoDealId?:""
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
                        label = "Potential Owner",
                        value = deal?.deal?.raw?.campaignSource?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Amount",
                        value = deal?.deal?.Amount?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Closing Date",
                        value =deal?.deal?.raw?.closingDate?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Account Name",
                        value = deal?.deal?.zohoDealId?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Pipeline",
                        value = deal?.deal?.raw?.pipeline?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Type",
                        value = deal?.deal?.raw?.type?:"",
                        icon = Icons.Outlined.Phone
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Stage",
                        value = deal?.deal?.raw?.stage?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                    ContactRow(
                        label = "Next Step",
                        value = deal?.deal?.raw?.nextStep?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                    ContactRow(
                        label = "Reason for Loss",
                        value = deal?.deal?.raw?.reasonForLossS?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                    ContactRow(
                        label = "Probability",
                        value = deal?.deal?.raw?.Probability.toString()?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                    ContactRow(
                        label = "Expected Revenue",
                        value = deal?.deal?.raw?.expectedRevenue.toString()?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp)
                    ContactRow(
                        label = "Lead Source",
                        value = deal?.deal?.raw?.leadSource?:"",
                        icon = Icons.Outlined.AccountBox
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ContactRow(
                        label = "Description",
                        value = deal?.deal?.raw?.description?:"",
                        icon = Icons.Outlined.MailOutline
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                }

                Spacer(modifier = Modifier.height(32.dp))

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
fun ActionButton1(icon: ImageVector, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = Color(0xFFF3F4F6),
            modifier = Modifier.size(56.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
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
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
fun ContactRow1(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = Color(0xFF9CA3AF),
                fontWeight = FontWeight.Medium
            )
        }
        // Copy icon often appears here, omitted to keep strictly to description
    }
}

@Composable
fun TimelineItem(title: String, time: String, description: String, icon: ImageVector, isFirst: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth()
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
                color = Color(0xFFE0E7FF), // Light indigo
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF4F46E5),
                    modifier = Modifier.size(10.dp).padding(5.dp)
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
                    color = Color.White
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
        EditContactScreen(rememberNavController(),"")
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