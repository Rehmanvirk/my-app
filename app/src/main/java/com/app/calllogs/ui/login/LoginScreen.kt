package com.app.calllogs.ui.login

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import com.app.calllogs.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.calllogs.utils.CardColor
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.TextColor

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    onLoggedIn: (token: String) -> Unit = {}
) {
    val state by vm.state.collectAsState()
    val uriHandler = LocalUriHandler.current

    // If token becomes available, navigate
    LaunchedEffect(state.token) {
        state.token?.let { onLoggedIn(it) }
    }

    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFF0B1722), Color(0xFF0A111A), Color(0xFF060B10))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(horizontal = 22.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Icon (placeholder square like screenshot)

            Image(painterResource(id = R.drawable.agentdesklog),contentDescription = "", modifier = Modifier.height(200.dp).width(250.dp))
//            Surface(
//                color = Color.White,
//                shape = RoundedCornerShape(14.dp),
//                modifier = Modifier.size(72.dp)
//            ) {
//                Box(contentAlignment = Alignment.Center) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.logo),
//                        contentDescription = ""
//                    )
////                    Text("📈", style = MaterialTheme.typography.headlineMedium)
//                }
//            }

//            Spacer(Modifier.height(18.dp))

//            Text(
//                text = "AgentDeskCRM",
//                style = MaterialTheme.typography.headlineLarge,
//                fontWeight = FontWeight.Bold,
//                color = TextColor
//            )
//            Spacer(Modifier.height(8.dp))
            Text(
                text = "Securely manage your leads and deals.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextColor
            )

            Spacer(Modifier.height(34.dp))

            // Email
            Text(
                text = "Email or Username",
                modifier = Modifier.fillMaxWidth(),
                color = TextColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("name@company.com") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_email_24),
                        contentDescription = "", tint = Color.Black
                    )
//                    Text("✉️")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = textFieldColorsDark()
            )

            Spacer(Modifier.height(16.dp))

            // Password
            Text(
                text = "Password",
                modifier = Modifier.fillMaxWidth(),
                color = TextColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("Enter your password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_password_24),
                        contentDescription = "", tint = Color.Black
                    )
//                    Text("🔒")
                },
                trailingIcon = {
                    IconButton(onClick = vm::togglePasswordVisibility) {
                        Icon(
                            painter = painterResource(id = if (state.passwordVisible) R.drawable.closeeye else R.drawable.eye),
                            contentDescription = "", tint = Color.Black, modifier = Modifier.size(25.dp)
                        )
//                        Text(if (state.passwordVisible) "🙈" else "👁️")
                    }
                },
                visualTransformation = if (state.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = textFieldColorsDark()
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* TODO forgot password flow */ }) {
                    Text("Forgot password?", color = TextColor)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Error
            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(10.dp))
            }

            // Log In button
            Button(
                onClick = vm::login,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColorD)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Log In   ➜",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Footer (privacy/terms)
            Row(
                modifier = Modifier
//                    .align(Alignment.BottomCenter)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { uriHandler.openUri("https://agentdeskcrm.com/privacy-policy") }) {
                    Text("PRIVACY POLICY", color = TextColor)
                }
                Text("•", color = TextColor)
                TextButton(onClick = { uriHandler.openUri("https://yourdomain.com/terms") }) {
                    Text("TERMS OF SERVICE", color = TextColor)
                }
            }
        }


    }
}

@Composable
@Preview
private fun loginScreenPre() {
    LoginScreen { }
}

@Composable
private fun textFieldColorsDark() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    disabledTextColor = Color(0xFF9FB0C0),

    focusedContainerColor = Color.White,//Color(0xFF0E2233),
    unfocusedContainerColor = Color.White,//Color(0xFF0E2233),
    disabledContainerColor = Color.White,//Color(0xFF0E2233),

    focusedBorderColor = Color(0xFF34526C),
    unfocusedBorderColor = Color(0xFF2A3F52),

    cursorColor = Color.Gray,
    focusedPlaceholderColor = Color.Gray,//Color(0xFF8EA0B2),
    unfocusedPlaceholderColor = Color.Gray,//Color(0xFF8EA0B2)
)