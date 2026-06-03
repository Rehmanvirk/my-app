package com.app.calllogs.ui.nav

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.calllogs.ui.leads.CreateLeadRoute
import com.app.calllogs.ui.leads.CreateLeadScreen
import com.app.calllogs.ui.leads.CreateLeadViewModel
import com.app.calllogs.ui.leads.LeadsViewModel

@Composable
fun CRMNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val leadsVM: LeadsViewModel = hiltViewModel()
    val createLeadsVM: CreateLeadViewModel = hiltViewModel()
    val state by createLeadsVM.state.collectAsState()

    NavHost (
        modifier = modifier,
        navController = navController,
        startDestination = Routes.LeadScreen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {


//        composable<Routes.CreateLeadScreen>(
//            enterTransition = { EnterTransition.None },
//            exitTransition = { ExitTransition.None }
//        ) {
//            CreateLeadRoute(createLeadsVM)
//        }
    }
}