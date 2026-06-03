package com.app.calllogs.ui.leads

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.calllogs.di.data.FieldValue
import com.app.calllogs.ui.dynamic.DateTimeInputField
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.formatDateTime
import com.app.calllogs.utils.millisToIso
import com.app.calllogs.utils.pickDateTime
import kotlin.collections.set

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertLeadScreen(
    navController: NavController,
    leadId: String,
    viewModel: EditLeadViewModel = hiltViewModel(),
) {

    var createContactChecked by remember { mutableStateOf(true) }
    var createAccountChecked by remember { mutableStateOf(false) }
    var createDealChecked by remember { mutableStateOf(false) }
    var includeAttachmentsChecked by remember { mutableStateOf(false) }
    var dealName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val lead by viewModel.lead.collectAsState()
    LaunchedEffect(leadId) {
        viewModel.getLeadDetails(leadId)
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<String>("") }
    val datePickerState = rememberDatePickerState()


    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Convert Lead",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardColorD,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White

                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White) // Light gray background
                .padding(paddingValues)
                .padding(8.dp).padding(bottom = 60.dp)
        ) {
            // Header Text
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar/Icon Placeholder
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE0E7FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null,
                        tint = Color(0xFF4338CA),
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = ("Convert " + lead?.lead?.dynamicFields?.fullName),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                    Text(
                        text = "Lead •"+ lead?.lead?.dynamicFields?.email, //john.doe@agentdeskrm.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // CONVERSION TARGETS SECTION
            Text(
                text = "CONVERSION TARGETS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Card for Targets
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ConvertRow(
                        title = "Contact",
                        subtitle = "Create a contact record",
                        checked = createContactChecked,
                        onCheckedChange = {
//                            createDealChecked = !it
                            createContactChecked = it }
                    )
                    Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                    ConvertRow(
                        title = "Account",
                        subtitle = "Create a account record",
                        checked = createAccountChecked,
                        onCheckedChange = {
//                            createDealChecked = !it
                            createAccountChecked = it }
                    )
                    Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                    ConvertRow(
                        title = "Deal",
                        subtitle = "Create a deal in pipeline",
                        checked = createDealChecked,
                        onCheckedChange = {
//                            createContactChecked = !it
                            createDealChecked = it }
                    )
                    if (createDealChecked){
                        Spacer(Modifier.height(8.dp))
                        SingleField(
                            label = "Deal Name",
                            value = dealName,
                            placeholder = "Deal 1",
                            onChange = {
                                dealName = it
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                        SingleField(
                            label = "Amount",
                            value = amount,
                            placeholder = "1000",
                            onChange = {
                                amount = it
                            }
                        )
                        Spacer(Modifier.height(8.dp))
//                        selectedDate = date
                        DateTimeInputField(
                            label = "Closing Date",
                            displayValue = selectedDate,
                            placeholder = "22-10-2025",
                            error = null,
                            onPick = {
                                showDatePicker = true
                            },
                            onClear = { },
//                        SingleField(
//                            label = "Closing Date",
//                            value = date,
//                            placeholder = "22-10-2025",
//                            onChange = {
//                                date = it
//                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // DATA & ATTACHMENTS SECTION
            Text(
                text = "DATA & ATTACHMENTS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7280),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Card for Data
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ConvertRow(
                        title = "Include existing attachments",
                        subtitle = "Move files to the new records",
                        checked = includeAttachmentsChecked,
                        onCheckedChange = { includeAttachmentsChecked = it }
                    )

//                    if (includeAttachmentsChecked) {
//                        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                imageVector = Icons.Outlined.Info,
//                                contentDescription = null,
//                                tint = Color(0xFF9CA3AF),
//                                modifier = Modifier.size(18.dp)
//                            )
//                            Text(
//                                text = "3 files will be transferred",
//                                style = MaterialTheme.typography.bodySmall,
//                                color = Color(0xFF6B7280)
//                            )
//                        }
//                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Warning Text
            Text(
                text = "Once converted, the lead record will be locked for further edits. Changes can be made in the created records.",
                style = MaterialTheme.typography.bodySmall,
                color = TextColor,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (state.error != null) {
                Text(
                    text = state.error?:"",
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                )
            }
            // Convert Button
            Button(
                onClick = {
                    if (createContactChecked){
                        viewModel.convertToContact(leadId)
                    }else if (createDealChecked){
                        viewModel.convertToDeal(leadId,dealName)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardColorD, // Blue
                    contentColor = Color.White
                )
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Convert Now",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        Button(onClick = {
                            selectedDate = millisToIso(datePickerState.selectedDateMillis!!)
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}

@Composable
fun ConvertRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF2563EB),
                checkedTrackColor = Color(0xFFBFDBFE),
                uncheckedThumbColor = Color(0xFFD1D5DB),
                uncheckedTrackColor = Color(0xFFF3F4F6)
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun ConvertLeadScreenPreview() {
//    ConvertLeadScreen()
}