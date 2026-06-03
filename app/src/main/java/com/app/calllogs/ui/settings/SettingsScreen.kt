package com.app.calllogs.ui.settings

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.login.LoginActivity
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.TextGreyColor
import javax.inject.Inject

@Composable
fun SettingsScreen(contentPadding: PaddingValues,) {
    // State for the toggle
    var darkModeEnabled by remember { mutableStateOf(true) } // Defaulting to ON based on description
    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B1722), Color(0xFF08101A), Color(0xFF060B10))
    )
    var isWebOpen by remember { mutableStateOf(false) }
    var webUrl by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val userPreference = UserPreference(context)

    if (isWebOpen){
        AccompanistWebView(webUrl)
    }

    Column(
        modifier = Modifier
            .fillMaxSize().padding(contentPadding)
            .background(Color.White) // Light gray/white background matching the screenshot
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // User Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
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
                // Profile Image Placeholder
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFFE5E7EB), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = userPreference.getUserPreference()?.user?.id?: "John Doe",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextColor
                    )
                    Text(
                        text = userPreference.getUserPreference()?.user?.role?:"Senior Agent",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = userPreference.getUserPreference()?.user?.email?:"john.doe@agentdeskrm.com",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // APP THEME Section
//        SectionHeader(title = "APP THEME")
//        Spacer(modifier = Modifier.height(12.dp))
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(containerColor = CardColor)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        imageVector = Icons.Outlined.Star,
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier.size(24.dp)
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Text(
//                        text = "Dark Mode",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = TextColor
//                    )
//                }
//                Switch(
//                    checked = darkModeEnabled,
//                    onCheckedChange = { darkModeEnabled = it },
//                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = Color(0xFF3B82F6),
//                        checkedTrackColor = Color(0xFFDBEAFE),
//                        uncheckedThumbColor = Color(0xFFD1D5DB),
//                        uncheckedTrackColor = Color(0xFFF3F4F6)
//                    )
//                )
//            }
//        }

//        Spacer(modifier = Modifier.height(24.dp))

        // ACCOUNT SETTINGS Section
        SectionHeader(title = "ACCOUNT SETTINGS")
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor)
        ) {
            SettingItem(
                icon = Icons.Outlined.Edit,
                title = "Update Profile",
                description = "Change your personal info",
                onItemClick = {
                    openUrlInBrowser(context, "https://agentdeskcrm.com/dashboard/profile")
                }
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            SettingItem(
                icon = Icons.Outlined.Notifications,
                title = "Notifications",
                description = "Manage alerts",
                onItemClick = {
                    openAppNotificationSettings(context)
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRIVACY & SECURITY Section
        SectionHeader(title = "PRIVACY & SECURITY")
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor)
        ) {
            SettingItem(
                icon = Icons.Outlined.Lock,
                title = "Security & Password",
                description = "Update password",
                onItemClick = {
                    openUrlInBrowser(context, "https://agentdeskcrm.com/terms-conditions")
                }
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            SettingItem(
                icon = Icons.Outlined.Settings,
                title = "Privacy Policy",
                description = "Read our policies",
                onItemClick = {
                    openUrlInBrowser(context, "https://agentdeskcrm.com/privacy-policy")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SUPPORT Section
        SectionHeader(title = "SUPPORT")
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardColor)
        ) {
            SettingItem(
                icon = Icons.Outlined.Info,
                title = "Help Center",
                description = "FAQs and guides",
                onItemClick = {
                    openUrlInBrowser(context, "https://agentdeskcrm.com/faq")
                }
            )
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            SettingItem(
                icon = Icons.Outlined.Person,
                title = "Contact Support",
                description = "Get in touch with us",
                onItemClick = {
                    openUrlInBrowser(context, "https://agentdeskcrm.com/contact-us")
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Logout Button
        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                intent.putExtra("logout",true)
                activity.startActivity(intent)
                activity.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF4444), // Red background
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer Version
        Text(
            text = "AGENTDESKCRM V1.0.0",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color(0xFF9CA3AF)
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextColor,
        letterSpacing = 0.5.sp
    )
}

@Composable
fun SettingItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, description: String,onItemClick : ()->Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4B5563),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = "Go",
            tint = Color.DarkGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 900)
@Composable
fun SettingsScreenPreview() {
//    SettingsScreen()
}

@Composable
fun AccompanistWebView(url: String) {
    // Jetpack Compose doesn't have a native WebView composable yet,
    // so we use AndroidView to embed a standard WebView.
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true // Enable JavaScript
            webViewClient = WebViewClient() // Prevents links from opening in external browser
            loadUrl(url)
        }
    }, update = { webView ->
        webView.loadUrl(url)
    })
}

fun openUrlInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Optional, useful in some contexts
    }
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: ActivityNotFoundException) {
        // Handle the case where no app can handle the intent (e.g., no browser installed)
        // You might show a Toast message to the user here
    }
}

fun openAppNotificationSettings(context: Context, channelId: String? = null) {
    val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (channelId != null) {
            // Intent to open a specific notification channel's settings
            Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
            }
        } else {
            // Intent to open the app's general notification settings
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
        }
    } else {
        // Fallback for devices running below Android O
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
    }

    // Add a flag to ensure the intent opens in a new task
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}