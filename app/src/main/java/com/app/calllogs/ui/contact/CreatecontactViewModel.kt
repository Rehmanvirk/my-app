package com.app.calllogs.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.di.data.CreateContactReq
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DynamicFields
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.ui.leads.CreateLeadUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateContactUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val newsletter: Boolean = true,

    val isSaving: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false
)

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val repo: ContactsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateLeadUiState())
    val state: StateFlow<CreateLeadUiState> = _state

    fun onFirstName(v: String) = _state.update { it.copy(firstName = v, error = null) }
    fun onLastName(v: String) = _state.update { it.copy(lastName = v, error = null) }
    fun onEmail(v: String) = _state.update { it.copy(email = v, error = null) }
    fun onPhone(v: String) = _state.update { it.copy(phone = v, error = null) }
    fun onNewsletter(v: Boolean) = _state.update { it.copy(newsletter = v) }

    // Hook these to pickers/bottom-sheets later
    fun setLeadSource(v: String?) = _state.update { it.copy(leadSource = v) }
    fun setLeadStatus(v: String) = _state.update { it.copy(leadStatus = v) }

    fun save() {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }
            val res = repo.createContact(
                CreateContactReq(
                    Last_Name = s.lastName.trim(),
                    Email = s.email.trim(),
                    Phone = s.phone.trim(),
                    First_Name = s.firstName
                )
            )

            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update { it.copy(isSaving = false, error = res.message) }
            }
        }
    }
}