package com.app.calllogs.ui.dynamic

import android.app.Activity
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.app.calllogs.di.data.DynamicFields
import com.app.calllogs.di.data.EditFields
import com.app.calllogs.di.data.FieldValue
import com.app.calllogs.di.data.Fields
import com.app.calllogs.di.data.LoginResponse
import com.app.calllogs.ui.leads.CreateLeadUiState

import com.app.calllogs.ui.leads.CreateLeadViewModel
import com.app.calllogs.ui.leads.EditLeadUiState
import com.app.calllogs.ui.login.LoginActivity
import com.app.calllogs.utils.CardColorD
import com.app.calllogs.utils.formatDate
import com.app.calllogs.utils.formatDateTime
import com.app.calllogs.utils.getDateMillis
import com.app.calllogs.utils.getTimeMillis
import com.app.calllogs.utils.getTimeMillisN
import com.app.calllogs.utils.pickDate
import com.app.calllogs.utils.pickDateTime
import com.app.calllogs.utils.relatedToModule
import com.app.calllogs.utils.relatedToRecordId
import com.app.calllogs.utils.relatedToZohoId
import com.app.calllogs.utils.toApiMap
import com.app.calllogs.utils.typography
import com.google.gson.Gson
import com.google.gson.JsonObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDynamicFormScreen(
    contentType: String,
    id: String,
    navController: NavController,
    vm: CreateLeadViewModel = hiltViewModel()
) {
    val state by vm.editState.collectAsState()


    val lifecycleOwner = LocalLifecycleOwner.current
    var title by remember { mutableStateOf("") }
    var isStarted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // ViewModel is of type BaseViewModel
            isStarted = true
            if (contentType == "lead") {
                title = "Edit Lead"
                vm.getEditLeadTemplate(id)
            } else if (contentType == "contact") {
                title = "Edit Contact"
                vm.getEditContactTemplate(id)
            } else if (contentType == "deal") {
                title = "Edit Deal"
                vm.getEditDealTemplate(id)
            }
//            else if (contentType == "task") {
//                title = "Create new Task"
//                vm.getTaskTemplate()
//            } else if (contentType == "meeting") {
//                title = "Create new Meeting"
//                vm.getMeetingTemplate()
//            } else if (contentType == "call") {
//                title = "Create new Call"
//                vm.getCallTemplate()
//            }
        }
    }
//    LaunchedEffect(state.saved) {
    if (state.saved) {
        state.saved = false
        navController.popBackStack()
    }
//    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), // Makes the Box take up the full available space
            contentAlignment = Alignment.Center // Centers the content (the indicator) inside the Box
        ) {
            CircularProgressIndicator()
        }
    }
//    else if (state.error!=null && state.error!="null"){
//        Toast.makeText(context,state.error, Toast.LENGTH_LONG).show()
////        val intent = Intent(context, LoginActivity::class.java)
////        intent.putExtra("logout",true)
////        activity.startActivity(intent)
////        activity.finish()
//    }
    else {
        if (state.error != null && state.error != "null")
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        EditDynamicFormScreen(navController, state, title, fields = state.fields, onCancel = {
            navController.popBackStack()
        }) { values ->

            val gson = Gson()
            val payload = toApiMap(values)
            val g =
                gson.toJsonTree(payload.filter { it.value != "" && it.value != "null" && it.value != null })
            val parent = JsonObject()
            parent.add("dynamicFields", g)
            println(parent)
            if (contentType == "lead") {
                vm.save(parent)
            } else if (contentType == "deal") {
                vm.saveDeal(g.asJsonObject)
            } else if (contentType == "contact") {
                vm.saveContact(g.asJsonObject)
            } else if (contentType == "task") {
                val taskObject = g.asJsonObject
                taskObject.addProperty("relatedToModule", relatedToModule)
                taskObject.addProperty("relatedToRecordId", relatedToZohoId)
                vm.saveTask(taskObject)
            } else if (contentType == "meeting") {
                val taskObject = g.asJsonObject
                taskObject.addProperty("relatedToModule", relatedToModule)
                taskObject.addProperty("relatedToRecord", relatedToZohoId)
                vm.saveMeeting(taskObject)
            } else if (contentType == "call") {
                val taskObject = g.asJsonObject
                taskObject.addProperty("relatedToModule", relatedToModule)
                taskObject.addProperty("relatedToRecordId", relatedToZohoId)
                taskObject.addProperty("callForModule", relatedToModule)
                taskObject.addProperty("callForRecordId", relatedToZohoId)
                taskObject.addProperty("\$se_module", relatedToModule)
                vm.saveCall(taskObject)
            } else
                vm.saveContact(g.asJsonObject)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDynamicFormScreen(
    navController: NavController,
    createState: EditLeadUiState,
    title: String,
    fields: List<EditFields>,
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onSubmit: (Map<String, FieldValue>) -> Unit
) {
    val context = LocalContext.current

    // Initialize state map with defaults
    val state = remember(fields) {
        mutableStateMapOf<String, FieldValue>().apply {
            fields.forEach { f ->
                put(
                    f.apiName,
                    when (f.dataType) {
                        "text" -> FieldValue.Text(f.value.toString())
                        "phone" -> FieldValue.Text(f.value.toString())
                        "datetime" -> FieldValue.DateTime(if(f.value!=null)getTimeMillisN(f.value.toString())else null)
                        "date" -> FieldValue.Date(if(f.value!=null)getTimeMillisN(f.value.toString())else null)
                        "picklist" -> FieldValue.Pick(f.value.toString())
                        "boolean" -> FieldValue.Select(f.value as? Boolean)
                        else -> FieldValue.Text(f.value.toString())
                    }
                )
            }
        }
    }

    // Simple validation
    val errors = remember { mutableStateMapOf<String, String>() }

    fun validate(): Boolean {
        errors.clear()
        fields.forEach { f ->
            if (!f.required) return@forEach

            val v = state[f.apiName]
            val missing = when (v) {
                is FieldValue.Text -> v.value.isBlank()
                is FieldValue.DateTime -> v.millis == null
                is FieldValue.Date -> v.millis == null
                is FieldValue.Pick -> v.value.isNullOrBlank()
                is FieldValue.Select -> v.value == false
                null -> true
            }
            if (missing) errors[f.apiName] = "${f.label} is required"
        }
        return errors.isEmpty()
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
//                    TextButton(onClick = onCancel) {
//                        Text("Cancel", color = Color.White)
//                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0A141E)
                )
            )
        }
    ) { contentPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//        Text("Dynamic Form", style = MaterialTheme.typography.titleLarge)

            fields.forEach { field ->
                val err = errors[field.apiName]
                when (field.dataType) {
                    "text" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Text ?: FieldValue.Text(field.value.toString()?:"")
                        TextInputField(
                            label = field.label + if (field.required) " *" else "",
                            value = current.value,
                            placeholder = field.label,
                            error = err,
                            onValueChange = { state[field.apiName] = FieldValue.Text(it) }
                        )
                    }

                    "phone" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Text ?: FieldValue.Text(field.value.toString()?:"")
                        TextInputField(
                            label = field.label + if (field.required) " *" else "",
                            value = current.value,
                            placeholder = field.label,
                            error = err,
                            onValueChange = { state[field.apiName] = FieldValue.Text(it) }
                        )
                    }

                    "email" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Text ?: FieldValue.Text(field.value.toString()?:"")
                        TextInputField(
                            label = field.label + if (field.required) " *" else "",
                            value = current.value,
                            placeholder = field.label,
                            error = err,
                            onValueChange = { state[field.apiName] = FieldValue.Text(it) }
                        )
                    }

                    "lookup" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Text ?: FieldValue.Text(field.value.toString()?:"")
                        TextInputField(
                            label = field.label + if (field.required) " *" else "",
                            value = current.value,
                            placeholder = field.label,
                            error = err,
                            onValueChange = { state[field.apiName] = FieldValue.Text(it) }
                        )
                    }

                    "currency" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Text ?: FieldValue.Text(field.value.toString()?:"")
                        TextInputField(
                            label = field.label + if (field.required) " *" else "",
                            value = current.value,
                            placeholder = field.label,
                            error = err,
                            onValueChange = { state[field.apiName] = FieldValue.Text(it) }
                        )
                    }

                    "datetime" -> {
                        if (field.apiName == "Created_Time" || field.apiName == "Modified_Time") {

                        } else {
                            val current = state[field.apiName] as? FieldValue.DateTime
                                ?: FieldValue.DateTime(if(field.value!=null)getTimeMillisN(field.value.toString())else null)
                            DateTimeInputField(
                                label = field.label + if (field.required) " *" else "",
                                displayValue = formatDateTime(current.millis),
                                placeholder = field.label ?: "Select date & time",
                                error = err,
                                onPick = {
                                    pickDateTime(context, current.millis) { pickedMillis ->
                                        state[field.apiName] = FieldValue.DateTime(pickedMillis)
                                    }
                                },
                                onClear = { state[field.apiName] = FieldValue.DateTime(null) }
                            )
                        }
                    }

                    "date" -> {
                        val current = state[field.apiName] as? FieldValue.Date
                            ?: FieldValue.Date(if(field.value!=null)getTimeMillisN(field.value.toString())else null)
                        DateTimeInputField(
                            label = field.label + if (field.required) " *" else "",
                            displayValue = formatDateTime(current.millis),
                            placeholder = field.label ?: "Select date",
                            error = err,
                            onPick = {
                                pickDate(context, current.millis) { pickedMillis ->
                                    state[field.apiName] = FieldValue.Date(pickedMillis)
                                }
                            },
                            onClear = { state[field.apiName] = FieldValue.Date(null) }
                        )
                    }

                    "picklist" -> {
                        val current =
                            state[field.apiName] as? FieldValue.Pick ?: FieldValue.Pick(null)
                        PickListField(
                            label = field.label + if (field.required) " *" else "",
                            options = field.options ?: emptyList(),
                            selected = current.value?:field.value.toString(),
                            placeholder = field.label ?: "Select",
                            error = err,
                            onSelect = { state[field.apiName] = FieldValue.Pick(it) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    validate()
                    if (validate()) onSubmit(state.toMap())
                },
                enabled = !createState.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CardColorD)
            ) {
                if (createState.isSaving) {
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

//            Button(
//                onClick = {
//
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Submit")
//            }
        }
    }
}

// -------------------- Field Composables --------------------

//@Composable
//fun TextInputField(
//    label: String,
//    value: String,
//    placeholder: String?,
//    error: String?,
//    onValueChange: (String) -> Unit
//) {
//    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            label = { Text(label) },
//            placeholder = { if (placeholder != null) Text(placeholder) },
//            isError = error != null,
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//        )
//        if (error != null) {
//            Text(
//                error,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}
//
//@Composable
//fun DateTimeInputField(
//    label: String,
//    displayValue: String,
//    placeholder: String,
//    error: String?,
//    onPick: () -> Unit,
//    onClear: () -> Unit
//) {
//    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//        OutlinedTextField(
//            value = displayValue,
//            onValueChange = { /* read-only */ },
//            label = { Text(label) },
//            placeholder = { Text(placeholder) },
//            isError = error != null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onPick() },
//            readOnly = true,
//            trailingIcon = {
//                Row {
//                    if (displayValue.isNotBlank()) {
//                        IconButton(onClick = onClear) { Text("✕") }
//                    }
//                    IconButton(onClick = onPick) { Text("📅") }
//                }
//            }
//        )
//        if (error != null) {
//            Text(
//                error,
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickListField(
    label: String,
    options: List<String>,
    selected: String?,
    placeholder: String,
    error: String?,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selected ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                placeholder = { Text(placeholder) },
                isError = error != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { opt ->
                    DropdownMenuItem(
                        text = { Text(opt) },
                        onClick = {
                            onSelect(opt)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (error != null) {
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}