package com.app.calllogs.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.app.calllogs.ui.nav.AppScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    companion object{
        var shouldOpen = false
        var module = "lead"
        var id = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        shouldOpen = intent?.getStringExtra("item") == "1"
        module = intent?.getStringExtra("module")?:""
        id = intent?.getStringExtra("id")?:""
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppScaffold(shouldOpen)
            }
        }
    }
}