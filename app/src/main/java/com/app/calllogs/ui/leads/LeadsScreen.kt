package com.app.calllogs.ui.leads

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.app.calllogs.R
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.ui.home.HomeActivity.Companion.module
import com.app.calllogs.ui.home.HomeActivity.Companion.shouldOpen
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.formatDate
import com.app.calllogs.utils.openDialer


@Composable
fun LeadsScreen(
    navController: NavController,
    contentPadding: PaddingValues,
    vm: LeadsViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current


//    LaunchedEffect(Unit) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            // ViewModel is of type BaseViewModel
//            vm.getLeads()
//        }
//    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.getLeads()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (shouldOpen) {
        if (module == "lead") {
            navController.navigate(Routes.CreateLeadScreen) {
                popUpTo(Routes.CreateLeadScreen) {
                    inclusive = true
                }
            }
        } else if (module == "deal") {
            navController.navigate(Routes.CreateDealScreen) {
                popUpTo(Routes.CreateDealScreen) {
                    inclusive = true
                }
            }
        } else if (module == "contact") {
            navController.navigate(Routes.CreateContactScreen) {
                popUpTo(Routes.CreateContactScreen) {
                    inclusive = true
                }
            }
        } else if (module == "task") {
            navController.navigate(Routes.CreateTaskScreen) {
                popUpTo(Routes.CreateTaskScreen) {
                    inclusive = true
                }
            }
        } else if (module == "meeting") {
            navController.navigate(Routes.CreateMeetingScreen) {
                popUpTo(Routes.CreateMeetingScreen) {
                    inclusive = true
                }
            }
        } else if (module == "call") {
            navController.navigate(Routes.CreateCallScreen) {
                popUpTo(Routes.CreateCallScreen) {
                    inclusive = true
                }
            }
        }
        shouldOpen = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(contentPadding)
            .padding(horizontal = 8.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        TopBar(onCreateLeade = {
            navController.navigate(Routes.CreateLeadScreen) {
                popUpTo(Routes.CreateLeadScreen) {
                    inclusive = true
                }
            }
        }, onRefresh = {vm.getLeads()})
        Spacer(Modifier.height(10.dp))

        SearchBar(
            value = state.query,
            onValueChange = vm::onQueryChange
        )

        Spacer(Modifier.height(6.dp))

        StatusChips(
            selected = state.selectedStatus,
            onSelected = vm::onStatusChange
        )

        Spacer(Modifier.height(6.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.isLoading) {
                items(5) { LoadingCard() }
            } else {
                items(
                    state.filtered.filter { it.dynamicFields.convertedS == false },
                    key = { it._id }) { lead ->
                    LeadCard(context, lead = lead, onClick = {
                        val idToPass = lead._id
//                        navController.navigate("LeadDetailsScreenRoute/$idToPass") {
//                            popUpTo("LeadDetailsScreenRoute"){
//                                this.inclusive = true
//                            }
//                        }
                        navController.navigate("EditLeadScreen/$idToPass") {
                            popUpTo("EditLeadScreen") {
                                this.inclusive = true
                            }
                        }
                        /* open lead details */
                    })
                }
            }
        }
    }
}

@Composable
fun TopBar(
    title: String = "",
    onCreateLeade: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // profile circle
        Surface(
            shape = CircleShape,
            color = CardColorD,//Color(0xFF0F2232),
            modifier = Modifier.size(42.dp)
        ) {
            Box(contentAlignment = Alignment.Center) { Text("👤", color = Color.White) }
        }

        Spacer(Modifier.width(12.dp))

        Text(
            title.ifEmpty { "Leads" },
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // filter icon
//        IconButton(onClick = { /* filter */ }) { Text("⏷", color = Color(0xFF95A6B8)) }

        Spacer(Modifier.width(8.dp))
        // refresh button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CardColorD,//Color(0xFF1677FF),
            modifier = Modifier.size(44.dp),
            onClick = {
                onRefresh()
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    "↻",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        // plus button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CardColorD,//Color(0xFF1677FF),
            modifier = Modifier.size(44.dp),
            onClick = {
                onCreateLeade()
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    "+",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TopBarWithBack(
    title: String = "",
    onCreateLeade: () -> Unit = {},
    onBackPress: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // profile circle

        IconButton(onClick = onBackPress) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
//        Surface(
//            shape = CircleShape,
//            color = CardColorD,//Color(0xFF0F2232),
//            modifier = Modifier.size(42.dp)
//        ) {
//            Box(contentAlignment = Alignment.Center) { Text("👤", color = Color.White) }
//        }

        Spacer(Modifier.width(12.dp))

        Text(
            title.ifEmpty { "Leads" },
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // filter icon
//        IconButton(onClick = { /* filter */ }) { Text("⏷", color = Color(0xFF95A6B8)) }

        Spacer(Modifier.width(8.dp))
        // refresh button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CardColorD,//Color(0xFF1677FF),
            modifier = Modifier.size(44.dp),
            onClick = {
                onRefresh()
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    "↻",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        // plus button
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = CardColorD,//Color(0xFF1677FF),
            modifier = Modifier.size(44.dp),
            onClick = {
                onCreateLeade()
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    "+",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = { Text("Search leads by name or status") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.outline_search_24),
                contentDescription = "", tint = Color.Black
            )
//            Text("🔍")
        },
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            focusedContainerColor = CardColor,//Color(0xFF0F2232),
            unfocusedContainerColor = CardColor,//Color(0xFF0F2232),
            focusedBorderColor = CardColor,//Color(0xFF0F2232),
            unfocusedBorderColor = CardColor,//Color(0xFF0F2232),
            focusedPlaceholderColor = Color.Gray, //Color(0xFF8EA0B2),
            unfocusedPlaceholderColor = Color.Gray,//Color(0xFF8EA0B2),
            cursorColor = Color.Black
        )
    )
}

@Composable
private fun StatusChips(selected: LeadStatus, onSelected: (LeadStatus) -> Unit) {
    val chips = listOf(
        LeadStatus.ALL to "All",
        LeadStatus.AttemptedtoContact to "Attempted to Contact",
        LeadStatus.ContactinFuture to "Contact in Future",
        LeadStatus.Contacted to "Contacted",
        LeadStatus.Junklead to "Junk lead",
        LeadStatus.Lostlead to "Lost lead",
        LeadStatus.Notcontacted to "Not contacted",
        LeadStatus.Prequalified to "Pre qualified",
        LeadStatus.Notqualified to "Not qualified"
    )
    val scrollState = rememberScrollState()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        chips.forEach { (status, label) ->
            val isSelected = selected == status
            FilterChip(
                selected = isSelected,
                onClick = {
                    onSelected(status)
                },
                label = {
                    Text(
                        label,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = CardColorD,//Color(0xFF1677FF),
                    containerColor = Color.White,
                    selectedLabelColor = Color.White,
                    labelColor = Color.Black
                ),
                border = null
            )
        }
    }
}

@Composable
private fun LeadCard(context: Context, lead: Lead, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = CardColor,//Color(0xFF0F2232),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(lead.dynamicFields.fullName?.get(0).toString())
            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    lead.dynamicFields.fullName ?: "",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusDot(lead.dynamicFields.leadStatus.orEmpty())
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "${statusLabel(lead.dynamicFields.leadStatus.orEmpty())} " + "\n" +
                                lead.dynamicFields.email.orEmpty() + "\n" +
                                lead.dynamicFields.phone.orEmpty(),
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 10.dp)) {
                IconButton(onClick = {
                    openDialer(context, lead.dynamicFields.phone ?: "03001234567")
                }) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Call",
                        tint = Color.Black
                    )
                }
                Text(
                    formatDate(lead.updatedAt),
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
//            Text("›", color = Color.DarkGray, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun Avatar(seed: String) {
    Surface(
        shape = CircleShape,
        color = Color(0xFF1B2F43),
        modifier = Modifier.size(34.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(seed.take(2), color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun StatusDot(status: String) {
    val c = when (status) {
        LeadStatus.AttemptedtoContact.name -> Color(0xFF1E88FF)
        LeadStatus.ContactinFuture.name -> Color(0xFFFF8A00)
        LeadStatus.Contacted.name -> Color(0xFF6E7D8C)
        LeadStatus.Junklead.name -> Color(0xFF00C27A)
        LeadStatus.Notcontacted.name -> Color(0xFF6E7D8C)
        LeadStatus.Prequalified.name -> Color(0xFF6E7D8C)
        LeadStatus.Notcontacted.name -> Color(0xFF6E7D8C)
        LeadStatus.ALL.name -> Color(0xFF6E7D8C)
        else -> {
            Color(0xFF1E88FF)
        }
    }
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(c, CircleShape)
    )
}

private fun statusLabel(status: String): String = when (status) {
    LeadStatus.ALL.name -> "All"
    LeadStatus.AttemptedtoContact.name -> "Attempted to Contact"
    LeadStatus.ContactinFuture.name -> "Contact in Future"
    LeadStatus.Contacted.name -> "Contacted"
    LeadStatus.Junklead.name -> "Junk lead"
    LeadStatus.Lostlead.name -> "Lost lead"
    LeadStatus.Notcontacted.name -> "Not contacted"
    LeadStatus.Prequalified.name -> "Pre qualified"
    LeadStatus.Notqualified.name -> "Not qualified"
    else ->
        status
}

@Composable
fun LoadingCard() {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = CardColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
    ) {}
}