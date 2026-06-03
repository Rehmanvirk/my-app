package com.app.calllogs.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.calllogs.R
import com.app.calllogs.di.data.Activity
import com.app.calllogs.di.data.FieldValue
import com.app.calllogs.di.data.Lead
import com.app.calllogs.ui.contact.EditContactScreen
import com.app.calllogs.ui.contact.EditContactViewModel
import com.app.calllogs.ui.dynamic.DateTimeInputField
import com.app.calllogs.ui.dynamic.TextInputField
import com.app.calllogs.ui.leads.ActionButton
import com.app.calllogs.ui.leads.ContactRow
import com.app.calllogs.ui.leads.EditLeadViewModel
import com.app.calllogs.ui.nav.Routes
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.TextColor
import com.app.calllogs.utils.bg
import com.app.calllogs.utils.openDialer
import com.app.calllogs.utils.pickDateTime
import com.app.calllogs.utils.typography
import kotlin.collections.set

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    id: String,
    viewModel: ActivitiesViewModel = hiltViewModel(),
) {

    val meeting by viewModel.state.collectAsState()
    val isLoading = meeting.isLoading
    val isSaved = meeting.isSaved
    val context = LocalContext.current
    // Fetch lead details if not already available
    LaunchedEffect(id) {
        viewModel.getTaskDetails(id)
    }

    var title by rememberSaveable  { mutableStateOf(meeting.tasksDet?.raw?.subject?:"") }
    var contactName by rememberSaveable  { mutableStateOf("") }
    var dueDate by rememberSaveable  { mutableStateOf("") }
    var status by rememberSaveable  { mutableStateOf("") }
    var periority by rememberSaveable  { mutableStateOf("") }
    var description by rememberSaveable  { mutableStateOf("") }

    LaunchedEffect(meeting.tasksDet) {
        title = meeting.tasksDet?.raw?.subject.orEmpty()
        contactName = meeting.tasksDet?.raw?.agent?:""
        dueDate =  meeting.tasksDet?.raw?.dueDate?:""
        status =  meeting.tasksDet?.raw?.status?:""
        description =  meeting.tasksDet?.raw?.description?:""
        periority = meeting.tasksDet?.raw?.priority?:""
    }

//    title = if (title.isEmpty()) meeting.tasksDet?.raw?.subject?:"" else ""




    if (!isLoading && isSaved){
        navController.popBackStack()
    }
    if (isLoading) {
//        CircularProgressIndicator(modifier = Modifier.wrapContentSize())
        Box(
            modifier = Modifier.fillMaxSize(), // Makes the Box take up the full available space
            contentAlignment = Alignment.Center // Centers the content (the indicator) inside the Box
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar (Back arrow)
            TopAppBar(
                title = { Text("Meeting Details", style = typography.headlineMedium)},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardColorD,
                    navigationIconContentColor = Color.White
                )
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                // Name
                Text(
                    text = meeting.tasksDet?.raw?.subject?:"",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Contact Information Section
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Task Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextColor
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    TextInputField(
                        label = "Title",
                        placeholder = null,
                        error = null,
                        value = title,
                        onValueChange = { title = it }
                    )
                    TextInputField(
                        label = "Due Date",
                        placeholder = null,
                        error = null,
                        value = dueDate,
                        onValueChange = {dueDate = it }
                    )
                    TextInputField(
                        label = "Contact Name",
                        placeholder = null,
                        error = null,
                        value = contactName,
                        onValueChange = {contactName = it }
                    )
                    TextInputField(
                        label = "Status",
                        placeholder = null,
                        error = null,
                        value = status,
                        onValueChange = {status = it }
                    )
                    TextInputField(
                        label = "Priority",
                        placeholder = null,
                        error = null,
                        value = periority,
                        onValueChange = {periority = it }
                    )

                    TextInputField(
                        label = "Description",
                        placeholder = null,
                        error = null,
                        value = description,
                        onValueChange = {description = it }
                    )
                }

//                Spacer(modifier = Modifier.height(32.dp))
                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.updateTask(id,title,description)
                    },
                    enabled = !meeting.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardColorD)
                ) {
                    if (meeting.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Save",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
