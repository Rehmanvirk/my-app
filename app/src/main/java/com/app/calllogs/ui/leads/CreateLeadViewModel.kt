package com.app.calllogs.ui.leads

import android.R.attr.country
import android.R.attr.description
import android.R.attr.editable
import android.R.attr.priority
import android.R.attr.rating
import android.R.attr.tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.database.AllContactEntity
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DynamicFields
import com.app.calllogs.di.data.EditFields
import com.app.calllogs.di.data.Fields
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.Template
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.LeadsRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateLeadUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val leadSource: String? = null,
    val leadStatus: String = "New",
    val newsletter: Boolean = true,

    val isSaving: Boolean = false,
    val isLoading : Boolean = false,
    val fields: List<Fields> = emptyList(),
    val error: String? = null,
    var saved: Boolean = false
)

data class EditLeadUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val leadSource: String? = null,
    val leadStatus: String = "New",
    val newsletter: Boolean = true,

    val isSaving: Boolean = false,
    val isLoading : Boolean = false,
    val fields: List<EditFields> = emptyList(),
    val error: String? = null,
    var saved: Boolean = false
)

@HiltViewModel
class CreateLeadViewModel @Inject constructor(
    private val repo: LeadsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateLeadUiState())
    val state: StateFlow<CreateLeadUiState> = _state

    private val _editState = MutableStateFlow(EditLeadUiState())
    val editState: StateFlow<EditLeadUiState> = _editState
    init {
        _state.update { it.copy(saved = false) }
        _editState.update { it.copy(saved = false) }
    }

    fun onFirstName(v: String) = _state.update { it.copy(firstName = v, error = null) }
    fun onLastName(v: String) = _state.update { it.copy(lastName = v, error = null) }
    fun onEmail(v: String) = _state.update { it.copy(email = v, error = null) }
    fun onPhone(v: String) = _state.update { it.copy(phone = v, error = null) }
    fun onNewsletter(v: Boolean) = _state.update { it.copy(newsletter = v) }

    // Hook these to pickers/bottom-sheets later
    fun setLeadSource(v: String?) = _state.update { it.copy(leadSource = v) }
    fun setLeadStatus(v: String) = _state.update { it.copy(leadStatus = v) }

    fun save(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createLead(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }

    fun saveContact(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createContact(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }

    fun getLeadTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getLeadsTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getContactTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getContactTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun getDealTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getDealTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun saveDeal(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createDeal(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }

    fun getTaskTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getTaskTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun saveTask(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createTask(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }
    fun getMeetingTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getMeetingTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun saveMeeting(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createMeeting(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }
    fun getCallTemplate() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getCallTemplate()) {
                is ApiResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.template.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
    fun saveCall(json : JsonObject) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.createCall(
                jsonObject = json

            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }

    fun getEditLeadTemplate(id: String) {
        viewModelScope.launch {
            _editState.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getEditLeadTemplate(id)) {
                is ApiResult.Success -> {
                    _editState.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.data.fields ?: emptyList()
                        )
                    }
                }

                is ApiResult.Error -> {
                    _editState.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getEditDealTemplate(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getEditDealTemplate(id)) {
                is ApiResult.Success -> {
                    _editState.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.data.fields ?: emptyList()
                        )
                    }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getEditContactTemplate(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, error = "null")
            }
            when (val res = repo.getEditContactTemplate(id)) {
                is ApiResult.Success -> {
                    _editState.update {
                        it.copy(
                            isLoading = false,
                            fields = res.data.data.fields ?: emptyList()
                        )
                    }
                }
                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
}