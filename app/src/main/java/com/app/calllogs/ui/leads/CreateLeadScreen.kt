package com.app.calllogs.ui.leads

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.DarkBackground
import com.app.calllogs.utils.TextColor
import kotlinx.coroutines.launch

private enum class SheetType { NONE, SOURCE, STATUS }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLeadRoute(
    navController: NavController,
    vm: CreateLeadViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navController.popBackStack()
        }
    }

    // Sheet controller
    var sheetType by remember { mutableStateOf(SheetType.NONE) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Data lists
    val leadSources = remember {
        listOf(
            "-None-",
            "Advertisement",
            "Cold Call",
            "Employee Referral",
            "External Referral",
            "Online Store",
            "Twitter",
            "Facebook",
            "Partner",
            "Google+",
            "Public Relations",
            "Sales Email Alias",
            "Seminar Partner",
            "Internal Seminar",
            "Trade Show",
            "Web Download",
            "Web Research",
            "Chat"
        )
    }
    val leadStatuses = remember {
        listOf(
            "All",
            "Attempted to Contact",
            "Contact in Future",
            "Contacted",
            "Junk lead",
            "Lost lead",
            "Not contacted",
            "Pre qualified",
            "Not qualified"
        )
    }

    CreateLeadScreen(
        state = state,
        onCancel = {
            navController.popBackStack()
        },
        onDone = { navController.popBackStack() },
        onSave = {
//            vm.save()
                 },
        onFirst = vm::onFirstName,
        onLast = vm::onLastName,
        onEmail = vm::onEmail,
        onPhone = vm::onPhone,
        onNewsletter = vm::onNewsletter,
        onPickSource = {
            sheetType = SheetType.SOURCE
            scope.launch { sheetState.show() }
        },
        onPickStatus = {
            sheetType = SheetType.STATUS
            scope.launch { sheetState.show() }
        }
    )
    if (sheetType != SheetType.NONE) {
        ModalBottomSheet(
            onDismissRequest = {
                sheetType = SheetType.NONE
            },
            sheetState = sheetState,
            containerColor = androidx.compose.ui.graphics.Color(0xFF0F2232),
            contentColor = androidx.compose.ui.graphics.Color.White,
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = androidx.compose.ui.graphics.Color(
                        0xFF2A3F52
                    )
                )
            }
        ) {
            val title = when (sheetType) {
                SheetType.SOURCE -> "Select Lead Source"
                SheetType.STATUS -> "Select Lead Status"
                else -> ""
            }

            SheetHeader(
                title = title,
                onClear = if (sheetType == SheetType.SOURCE) {
                    {
                        vm.setLeadSource(null)
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { sheetType = SheetType.NONE }
                    }
                } else null
            )

            val items = when (sheetType) {
                SheetType.SOURCE -> leadSources
                SheetType.STATUS -> leadStatuses
                else -> emptyList()
            }

            val selected = when (sheetType) {
                SheetType.SOURCE -> state.leadSource
                SheetType.STATUS -> state.leadStatus
                else -> null
            }

            SheetList(
                items = items,
                selectedValue = selected,
                onSelect = { picked ->
                    when (sheetType) {
                        SheetType.SOURCE -> vm.setLeadSource(picked)
                        SheetType.STATUS -> vm.setLeadStatus(picked)
                        else -> {}
                    }
                    scope.launch { sheetState.hide() }
                        .invokeOnCompletion { sheetType = SheetType.NONE }
                }
            )

            Spacer(Modifier.height(18.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLeadScreen(
    state: CreateLeadUiState,
    onCancel: () -> Unit,
    onDone: () -> Unit,
    onSave: () -> Unit,
    onFirst: (String) -> Unit,
    onLast: (String) -> Unit,
    onEmail: (String) -> Unit,
    onPhone: (String) -> Unit,
    onNewsletter: (Boolean) -> Unit,
    onPickSource: () -> Unit,
    onPickStatus: () -> Unit
) {
    val bg = Brush.verticalGradient(
        listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
    )

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Create New Lead",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onCancel) {
                        Text("Cancel", color = Color.White)
                    }
                },
                actions = {
                    TextButton(
                        onClick = onDone,
                        enabled = !state.isSaving
                    ) {
                        Text("Done", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CardColorD,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
                .padding(bottom = 22.dp)
        ) {
            Spacer(Modifier.height(14.dp))

            SectionHeader("PERSONAL INFORMATION")
            GroupCard {
                TwoColumnFields(
                    leftLabel = "FIRST NAME *",
                    leftValue = state.firstName,
                    leftPlaceholder = "John",
                    onLeftChange = onFirst,
                    rightLabel = "LAST NAME *",
                    rightValue = state.lastName,
                    rightPlaceholder = "Doe",
                    onRightChange = onLast
                )
            }

            Spacer(Modifier.height(16.dp))

            SectionHeader("CONTACT DETAILS")
            GroupCard {
                SingleField(
                    label = "EMAIL *",
                    value = state.email,
                    placeholder = "john.doe@example.com",
                    onChange = onEmail
                )
                DividerLine()
                SingleField(
                    label = "PHONE NUMBER *",
                    value = state.phone,
                    placeholder = "(555) 000-0000",
                    onChange = onPhone
                )
            }

            Spacer(Modifier.height(16.dp))

            SectionHeader("CLASSIFICATION")
            GroupCard {
                PickerRow(
                    title = "Lead Source",
                    value = state.leadSource ?: "Select Source",
                    valueColor = if (state.leadSource == null) Color(0xFF1677FF) else Color(
                        0xFF1677FF
                    ),
                    onClick = onPickSource
                )
                DividerLine()
                PickerRow(
                    title = "Lead Status",
                    value = state.leadStatus,
                    valueColor = Color(0xFF1677FF),
                    onClick = onPickStatus
                )
            }

            Spacer(Modifier.height(16.dp))

            SectionHeader("PREFERENCES")
            GroupCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Newsletter Subscription",
                        color = TextColor,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = state.newsletter,
                        onCheckedChange = onNewsletter,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF1677FF),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFF3A4B5C)
                        )
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                )
            }

            Button(
                onClick = onSave,
                enabled = !state.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColorD)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Save Lead",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(Modifier.height(55.dp))
        }
    }
}

/* ---------- UI building blocks ---------- */

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        color = TextColor,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
    )
}

@Composable
fun GroupCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        color = CardColor,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(content = content)
    }
}

@Composable
fun DividerLine() {
    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
}

@Composable
fun TwoColumnFields(
    leftLabel: String,
    leftValue: String,
    leftPlaceholder: String,
    onLeftChange: (String) -> Unit,
    rightLabel: String,
    rightValue: String,
    rightPlaceholder: String,
    onRightChange: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            FieldLabel(leftLabel)
            Spacer(Modifier.height(8.dp))
            DarkTextField(
                value = leftValue,
                placeholder = leftPlaceholder,
                onChange = onLeftChange
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Color(0xFF1A3146))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            FieldLabel(rightLabel)
            Spacer(Modifier.height(8.dp))
            DarkTextField(
                value = rightValue,
                placeholder = rightPlaceholder,
                onChange = onRightChange
            )
        }
    }
}

@Composable
fun SingleField(
    label: String,
    value: String,
    placeholder: String,
    onChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
        FieldLabel(label)
        Spacer(Modifier.height(8.dp))
        DarkTextField(value = value, placeholder = placeholder, onChange = onChange)
    }
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        color = TextColor,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun DarkTextField(
    value: String,
    placeholder: String,
    onChange: (String) -> Unit
) {
    // Looks like iOS-style inline text (no border)
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = { Text(placeholder, color = Color(0xFF516579)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun PickerRow(
    title: String,
    value: String,
    valueColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            color = TextColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        Text(value, color = valueColor, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(8.dp))
        Text("›", color = Color(0xFF8EA0B2), style = MaterialTheme.typography.headlineSmall)
    }


}