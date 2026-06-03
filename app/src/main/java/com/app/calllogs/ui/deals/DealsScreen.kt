package com.app.calllogs.ui.deals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.di.data.Deal
import com.app.calllogs.ui.leads.LoadingCard
import com.app.calllogs.ui.leads.TopBar
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.formatAsUSDCurrency
import com.app.calllogs.utils.formatDate
import com.app.calllogs.utils.getStageColor

private enum class DealType { ACTIVE, WON, LOST }

@Composable
fun DealsScreen(
    navController: NavController,
    contentPadding: PaddingValues,
    vm: DealsViewModel = hiltViewModel()
) {
    // Define the data as described in the document
//    val deals = rememberDeals()
    val state by vm.state.collectAsState()
    var dealType by remember { mutableStateOf(DealType.ACTIVE) }
    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
    )

    var totalAmount by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(Color.White) // Light gray background
            .padding(8.dp)
    ) {
        // Header Section
        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Deals",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                color = TextColor
//            )
            TopBar(title = "Deals", onCreateLeade = {
                navController.navigate(Routes.CreateDealScreen) {
                    popUpTo(Routes.CreateDealScreen) {
                        inclusive = true
                    }
                }
            }, onRefresh = {vm.getLeads()})
            Spacer(modifier = Modifier.height(16.dp))

            // Tabs Section
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.Start,modifier = Modifier.clickable(onClick = {
                    vm.onQueryChange("")
                    dealType = DealType.ACTIVE})) {
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (dealType.name == DealType.ACTIVE.name) FontWeight.Bold else FontWeight.Normal,
                        color = if (dealType.name == DealType.ACTIVE.name) Color(0xFF2563EB) else Color(
                            0xFF9CA3AF
                        )// Blue for active tab
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (dealType.name == DealType.ACTIVE.name) {
                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(3.dp)
                                .background(Color(0xFF2563EB), RoundedCornerShape(2.dp))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(horizontalAlignment = Alignment.Start,modifier = Modifier.clickable(onClick = {
                    vm.onQueryChange("Closed Won")
                    dealType = DealType.WON})) {
                    Text(
                        text = "Won",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (dealType.name == DealType.WON.name) FontWeight.Bold else FontWeight.Normal,
                        color = if (dealType.name == DealType.WON.name) Color(0xFF2563EB) else Color(
                            0xFF9CA3AF
                        )// Blue for active tab
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (dealType.name == DealType.WON.name) {
                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(3.dp)
                                .background(Color(0xFF2563EB), RoundedCornerShape(2.dp))
                        )
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.clickable(onClick = {
                    vm.onQueryChange("Closed Lost")
                    dealType = DealType.LOST})) {
                    Text(
                        text = "Lost",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (dealType.name == DealType.LOST.name) FontWeight.Bold else FontWeight.Normal,
                        color = if (dealType.name == DealType.LOST.name) Color(0xFF2563EB) else Color(
                            0xFF9CA3AF
                        )// Blue for active tab
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (dealType.name == DealType.LOST.name) {
                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(3.dp)
                                .background(Color(0xFF2563EB), RoundedCornerShape(2.dp))
                        )
                    }
                }
//                Text(
//                    text = "Won",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = Color(0xFF9CA3AF) // Gray for inactive
//                )
//                Spacer(modifier = Modifier.width(24.dp))
//                Text(
//                    text = "Lost",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = Color(0xFF9CA3AF)
//                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pipeline Value Section
        Text(
            text = "PIPELINE VALUE",
            style = MaterialTheme.typography.labelLarge,
            color = Color.LightGray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = totalAmount(state.allDeals).formatAsUSDCurrency(),//"$2,845,000",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = TextColor,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Deals List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading) {
                items(5) { LoadingCard() }
            } else {
                items(state.filtered) { deal ->
//                totalAmount = totalAmount+deal.Amount.toDouble()
                    DealCard(deal = deal, onClick = {
                        val idToPass = deal._id
                        navController.navigate("EditDealScreen/$idToPass") {
                            popUpTo("EditDealScreen") {
                                this.inclusive = true
                            }
                        }
                    })
                }
            }
        }
    }
}

private fun totalAmount(deal: List<Deal>) : Double{
    return deal.sumOf { it.Amount?.toDouble() ?: 0.0 }

}
@Composable
fun DealCard(deal: Deal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = { onClick() })
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFFEFF6FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content Column
            Column(modifier = Modifier.weight(1f)) {
                // Top Row: Name and Amount
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = deal.Deal_Name.orEmpty(),
                        Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                    Text(
                        text = "$"+deal.Amount?.toDouble()?.formatAsUSDCurrency(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                }
// Stage Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = getStageColor(deal.Stage.orEmpty()).first
                ) {
                    Text(
                        text = deal.Stage.orEmpty(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = getStageColor(deal.Stage.orEmpty()).second,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Middle Row: Date and Stage Pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formatDate(deal.createdAt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Section
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progress",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF9CA3AF)
                        )
                        Text(
                            text = "${((deal.raw?.Probability?.toFloat()?.toInt() ?: 10))}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF3B82F6),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { deal.raw?.Probability?.toFloat()?.div(100) ?: 0.1f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(Color(0xFFF3F4F6), RoundedCornerShape(3.dp)),
                        color = Color(0xFF3B82F6),
                        trackColor = Color.Transparent,
                    )
                }
            }
        }
    }
}

// Data Source
//fun rememberDeals(): List<Deal> {
//    return listOf(
//        Deal(
//            _id = "737879",
//            Deal_Name = "Modern Office Complex",
//            Amount = "$450,000",
//            Stage = "Negotiation",
//            createdAt = "Oct 24, 2023",
//            progress = 0.75f,
////            icon = Icons.Outlined.Info
//        ),
//        Deal(
//            _id = "737879",
//            Deal_Name = "Downtown Penthouse",
//            Amount = "$1,200,000",
//            Stage = "Proposal",
//            createdAt = "Nov 12, 2023",
//            progress = 0.40f,
////            icon = Icons.Outlined.Info
//        ),
//        Deal(
//            _id = "737879",
//            Deal_Name = "Riverside Estate",
//            Amount = "$895,000",
//            Stage = "Closing",
//            createdAt = "Sep 30, 2023",
//            progress = 0.95f,
////            icon = Icons.Outlined.Home
//        ),
//        Deal(
//            _id = "737879",
//            Deal_Name = "Commercial Retail Space",
//            Amount = "$300,000",
//            Stage = "Discovery",
//            createdAt = "Dec 05, 2023",
//            progress = 0.15f,
////            icon = Icons.Outlined.Info
//        )
//    )
//}

@Preview(showBackground = true, widthDp = 400, heightDp = 800)
@Composable
fun DealsScreenPreview() {
    MaterialTheme {
//        DealsScreen(rememberNavController())
    }
}