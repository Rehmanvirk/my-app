package com.app.calllogs.ui.tasks

import android.R.attr.fontWeight
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.calllogs.di.data.Calls
import com.app.calllogs.di.data.Contacts
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.Tasks
import com.app.calllogs.ui.contact.ContactsViewModel
import com.app.calllogs.ui.leads.Avatar
import com.app.calllogs.ui.leads.LeadsViewModel
import com.app.calllogs.ui.leads.LoadingCard
import com.app.calllogs.ui.leads.SearchBar
import com.app.calllogs.ui.leads.TopBar
import com.app.calllogs.ui.leads.TopBarWithBack
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.DarkBackground
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.formatDate
import com.app.calllogs.utils.relatedToModule
import com.app.calllogs.utils.relatedToRecordId

@Composable
fun CallScreen(
    navController: NavController,
    contentPadding: PaddingValues,
    vm: ActivitiesViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
    )

    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(Unit) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            // ViewModel is of type BaseViewModel
//            vm.getTasks(relatedToModule, relatedToRecordId)
//        }
//    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.getTasks(relatedToModule, relatedToRecordId)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(contentPadding)
            .padding(horizontal = 8.dp)
    ) {
        Spacer(Modifier.height(10.dp))
        TopBarWithBack(title = "Calls", onCreateLeade = {
            navController.navigate(Routes.CreateCallScreen) {
                popUpTo(Routes.CreateCallScreen) {
                    this.inclusive = true
                }
            }
        }, onBackPress = {
            navController.popBackStack()
        }, onRefresh = {vm.getTasks(relatedToModule, relatedToRecordId)})
        Spacer(Modifier.height(16.dp))

//        SearchBar(
//            value = state.query,
//            onValueChange = vm::onQueryChange
//        )
//
//        Spacer(Modifier.height(8.dp))
        if (!state.isLoading && state.allCalls.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(), // Makes the Box take up the full available space
                contentAlignment = Alignment.Center // Centers the content (the indicator) inside the Box
            ) {
                Text(
                    "No Call Logs",
                    color = TextColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {

            LazyColumn(
                contentPadding = PaddingValues(bottom = 18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading) {
                    items(5) { LoadingCard() }
                } else {
                    items(state.allCalls) { lead ->
                        CallCard(lead = lead, onClick = {
                            val idToPass = lead.id
                            navController.navigate("EditCallScreen/$idToPass") {
                                popUpTo("EditCallScreen"){
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
}


@Composable
private fun CallCard(lead: Calls, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = CardColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(lead.raw?.subject ?: "")
            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    lead.raw?.subject ?: "",
                    color = TextColor,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = lead.raw?.description ?: "No description available",
                    color = TextColor,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = lead.raw?.callPurpose ?: "",
                        color = TextColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = formatDate(lead.updatedAt.orEmpty()),
                        color = TextColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text("›", color = Color.DarkGray, style = MaterialTheme.typography.headlineSmall)
        }
    }
}