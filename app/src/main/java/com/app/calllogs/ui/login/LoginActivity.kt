package com.app.calllogs.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val logout = intent.getBooleanExtra("logout",false)
        if (logout){
            userPreference.clearStorage()
        }else {
            if ((userPreference.getUserPreference()?.accessToken ?: "").isNotEmpty()) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                LoginScreen(
                    onLoggedIn = { token ->
                        // TODO navigate to home screen
                         startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}