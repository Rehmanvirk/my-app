package com.app.calllogs.ui.tasks

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.di.data.Calls
import com.app.calllogs.di.data.Contacts
import com.app.calllogs.di.data.Deal
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.data.Meetings
import com.app.calllogs.di.data.Tasks
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ActivitiesRepository
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.DealsRepository
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.contact.ContactsUiState
import com.app.calllogs.ui.home.HomeActivity.Companion.module
import com.app.calllogs.ui.leads.LeadsUiState
import com.app.calllogs.ui.nav.BottomRoute
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val repo: ActivitiesRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _state = MutableStateFlow(DealsUiState(isLoading = true))
    val state: StateFlow<DealsUiState> = _state

    init {
        _state.update { it.copy(isSaved = false) }
    }
    fun onQueryChange(v: String) = _state.update { it.copy(query = v) }
//    fun onStatusChange(s: LeadStatus) = _state.update { it.copy(selectedStatus = s) }

    fun getTasks(module : String,zohoId : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }
            delay(1000)
            when (val res = repo.getActivities(module,zohoId)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, allTasks = res.data.tasks?:emptyList(), allCalls = res.data.calls?:emptyList(),allMeetings = res.data.meetings?:emptyList()) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getTaskDetails(id : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isSaved = false, error = "null") }
            when (val res = repo.getTaskDetails(id)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, tasksDet = res.data.record) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun updateTask(id : String,title : String, desc : String) {
        viewModelScope.launch {
            val parent = JsonObject()
            parent.addProperty("Subject", title)
            parent.addProperty("Description", desc)
            _state.update { it.copy(isSaving = true, error = "null") }
            when (val res = repo.updateTaskDetails(id, jsonObject = parent)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isSaving = false, isSaved = true) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isSaving = false, error = res.message) }
                }
            }
        }
    }

    fun getMeetingDetails(id : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true,isSaved = false, error = "null") }
            when (val res = repo.getMeetingDetails(id)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, meetingsDet = res.data.record) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun updateMeeting(id : String,title : String, desc : String) {
        viewModelScope.launch {
            val parent = JsonObject()
            parent.addProperty("Event_Title", title)
            parent.addProperty("Description", desc)
            _state.update { it.copy(isSaving = true, error = "null") }
            when (val res = repo.updateMeetingDetails(id, jsonObject = parent)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isSaving = false, isSaved = true) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isSaving = false, error = res.message) }
                }
            }
        }
    }

    fun getCallDetails(id : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isSaved = false,error = "null") }
            when (val res = repo.getCallDetails(id)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, callsDet = res.data.record) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun updateCall(id : String,title : String, desc : String) {
        viewModelScope.launch {
            val parent = JsonObject()
            parent.addProperty("Subject", title)
            parent.addProperty("Description", desc)
            _state.update { it.copy(isSaving = true, error = "null") }
            when (val res = repo.updateCallDetails(id, jsonObject = parent)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isSaving = false, isSaved = true) }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isSaving = false, error = res.message) }
                }
            }
        }
    }
}



data class DealsUiState(
    val query: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val allTasks: List<Tasks> = emptyList(),
    val allMeetings: List<Meetings> = emptyList(),
    val allCalls: List<Calls> = emptyList(),

    val isSaving : Boolean = false,
    val isSaved : Boolean = false,
    val tasksDet: Tasks? = null,
    val meetingsDet: Meetings? = null,
    val callsDet: Calls? = null,

    ) {
//    val filtered: List<Deal>
//        get() {
//            val q = query.trim().lowercase()
//            return allTasks
////                .filter { selectedStatus == LeadStatus.ALL || it.dynamicFields.Lead_Status == selectedStatus.name }
//                .filter {
//                    q.isEmpty() || it.Stage.orEmpty().lowercase().contains(q)
//                }
//        }
}