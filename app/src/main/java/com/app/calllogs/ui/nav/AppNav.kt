package com.app.calllogs.ui.nav

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.app.calllogs.R
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.chat.ChatDetailScreen
import com.app.calllogs.ui.chat.ChatsScreen
import com.app.calllogs.ui.contact.ContactScreen
import com.app.calllogs.ui.contact.CreateContactRoute
import com.app.calllogs.ui.contact.EditContactScreen
import com.app.calllogs.ui.deals.DealsScreen
import com.app.calllogs.ui.deals.EditDealScreen
import com.app.calllogs.ui.dynamic.DynamicFormScreen
import com.app.calllogs.ui.dynamic.EditDynamicFormScreen
import com.app.calllogs.ui.leads.ConvertLeadScreen
import com.app.calllogs.ui.leads.CreateLeadRoute
import com.app.calllogs.ui.leads.CreateLeadViewModel
import com.app.calllogs.ui.leads.EditLeadScreen
import com.app.calllogs.ui.leads.EditLeadViewModel
import com.app.calllogs.ui.leads.LeadsScreen
import com.app.calllogs.ui.leads.LeadsViewModel
import com.app.calllogs.ui.settings.SettingsScreen
import com.app.calllogs.ui.tasks.CallScreen
import com.app.calllogs.ui.tasks.EditCallScreen
import com.app.calllogs.ui.tasks.EditMeetingScreen
import com.app.calllogs.ui.tasks.EditTaskScreen
import com.app.calllogs.ui.tasks.MeetingScreen
import com.app.calllogs.ui.tasks.TaskScreen
import com.app.calllogs.utils.CardColor
import javax.inject.Inject


sealed class BottomRoute(val route: String, val label: String, @DrawableRes val iconResId: Int) {
    data object Leads : BottomRoute("leads", "Leads", R.drawable.lead)
    data object Contacts : BottomRoute("contacts", "Contacts", R.drawable.contact)
    data object Chat : BottomRoute("chat", "Chat", R.drawable.chat)
    data object Deals : BottomRoute("deals", "Deals", R.drawable.deal)
    data object Settings : BottomRoute("settings", "Settings", R.drawable.setting)
}

private val bottomItems = listOf(
    BottomRoute.Leads,
    BottomRoute.Contacts,
    BottomRoute.Chat,
    BottomRoute.Deals,
    BottomRoute.Settings
)

@Composable
fun AppScaffold(openCreate: Boolean) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val leadsVM: LeadsViewModel = hiltViewModel()
    val createLeadsVM: CreateLeadViewModel = hiltViewModel()
    val editLeadViewModel: EditLeadViewModel = hiltViewModel()
    val state by createLeadsVM.state.collectAsState()
    val showBottomBar = currentRoute in listOf(
        BottomRoute.Leads.route,
        BottomRoute.Contacts.route,
        BottomRoute.Chat.route,
        BottomRoute.Deals.route,
        BottomRoute.Settings.route
    )
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ) {
                    bottomItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconResId),
                                    contentDescription = item.label, modifier = Modifier.size(30.dp)
                                )
                            },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White, //Color(0xFF1677FF),
                                selectedTextColor = Color.Black, //Color(0xFF1677FF),
                                unselectedIconColor = Color(0xFF95A6B8),
                                unselectedTextColor = Color(0xFF95A6B8),
                                indicatorColor = Color(0xFF0F2232),
                            )
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomRoute.Leads.route
        ) {
            composable(BottomRoute.Leads.route) {
                LeadsScreen(
                    navController,
                    contentPadding = padding
                )
            }

            composable("ConvertLeadScreen/{leadId}")
            { backStackEntry ->
                val leadId = backStackEntry.arguments?.getString("leadId")
                if (leadId != null)
                    ConvertLeadScreen(
                        navController,
                        leadId,
                        editLeadViewModel
                    )
            }

//            composable<Routes.CreateLeadScreen>(
//                enterTransition = { EnterTransition.None },
//                exitTransition = { ExitTransition.None }
//            ) {
//                CreateLeadRoute(navController, createLeadsVM)
//            }

            composable<Routes.CreateLeadScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("lead", navController, createLeadsVM)
            }
            composable<Routes.CreateTaskScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("task", navController, createLeadsVM)
            }
            composable<Routes.TaskListScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                TaskScreen( navController, padding)
            }
            composable("EditTaskScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditTaskScreen(
                        navController,
                        id,
                    )
            }
            composable<Routes.MeetingistScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                MeetingScreen( navController, padding)
            }
            composable("EditMeetingScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditMeetingScreen(
                        navController,
                        id,
                    )
            }
            composable<Routes.CallListScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                CallScreen( navController, padding)
            }
            composable("EditCallScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditCallScreen(
                        navController,
                        id,
                    )
            }
            composable<Routes.CreateMeetingScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("meeting", navController, createLeadsVM)
            }
            composable<Routes.CreateCallScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("call", navController, createLeadsVM)
            }

            composable("EditLeadScreen/{leadId}")
            { backStackEntry ->
                val leadId = backStackEntry.arguments?.getString("leadId")
                if (leadId != null)
                    EditLeadScreen(
                        navController,
                        leadId,
                        editLeadViewModel
                    )
            }
            composable(BottomRoute.Contacts.route) { ContactScreen(navController, padding) }
//            composable<Routes.CreateContactScreen>(
//                enterTransition = { EnterTransition.None },
//                exitTransition = { ExitTransition.None }
//            ) {
//                CreateContactRoute(navController)
//            }
            composable<Routes.CreateContactScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("contact", navController, createLeadsVM)
            }

            composable("EditContactScreen/{contactId}")
            { backStackEntry ->
                val contactId = backStackEntry.arguments?.getString("contactId")
                if (contactId != null)
                    EditContactScreen(
                        navController,
                        contactId,
                    )
            }
            composable(BottomRoute.Chat.route) { ChatsScreen(navController, padding) }
            composable("Routes.ChatDetailsScreen/{conversationId}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("conversationId")
                ChatDetailScreen(navController, padding, id ?: "")
            }
            composable(BottomRoute.Deals.route) {
                DealsScreen(
                    navController = navController,
                    padding
                )
            }
            composable<Routes.CreateDealScreen>(
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None }
            ) {
                DynamicFormScreen("deal", navController, createLeadsVM)
            }
            composable("EditDealScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditDealScreen(
                        navController,
                        id,
                    )
            }

            composable("EditLeadDynamicScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditDynamicFormScreen(
                        "lead",
                        id,
                        navController
                    )
            }
            composable("EditContactDynamicScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditDynamicFormScreen(
                        "contact",
                        id,
                        navController
                    )
            }
            composable("EditDealDynamicScreen/{id}")
            { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                if (id != null)
                    EditDynamicFormScreen(
                        "deal",
                        id,
                        navController
                    )
            }
            composable(BottomRoute.Settings.route) { SettingsScreen(padding) }
        }
    }
}

@Composable
private fun PlaceholderScreen(
    title: String,
    padding: androidx.compose.foundation.layout.PaddingValues
) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(title, color = Color.White)
    }
}