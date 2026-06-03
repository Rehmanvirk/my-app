package com.app.calllogs.ui.leads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.di.data.ConvertLeadToContact
import com.app.calllogs.di.data.ConvertLeadToDeal
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.Deal
import com.app.calllogs.di.data.DealData
import com.app.calllogs.di.data.DynamicFields
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ApiInterface
import com.app.calllogs.di.repository.LeadsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditLeadViewModel @Inject constructor(
    private val repo: LeadsRepository
) : ViewModel() {

    private val _lead = MutableStateFlow<LeadDetailsResponse?>(null)
    val lead: StateFlow<LeadDetailsResponse?> = _lead

    private val _state = MutableStateFlow(CreateLeadUiState())
    val state: StateFlow<CreateLeadUiState> = _state
    fun getLeadDetails(leadId: String) {
        viewModelScope.launch {
            val res = repo.getLeadDetails(leadId)
            when (res) {
                is ApiResult.Success -> _lead.value = res.data
                is ApiResult.Error -> _lead.value = LeadDetailsResponse(message = "no data found")
            }
        }
    }

    fun convertToContact(leadId: String) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.convertLeadToContact(
                leadId,
                ConvertLeadToContact(
                    createDeal = false,
                    moveAttachmentsTo = "Contacts"

                )
            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update {
                    it.copy(
                        isSaving = false,
                        error = res.message
                    )
                }
            }
        }
    }

    fun convertToDeal(leadId: String, dealname: String) {
        val s = state.value
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, error = null, saved = false) }

            val res = repo.convertLeadToDeal(
                leadId,
                ConvertLeadToDeal(
                    createDeal = true,
                    moveAttachmentsTo = "Deals",
                    dealData = DealData(
                        Deal_Name = dealname
                    )

                )
            )
            when (res) {
                is ApiResult.Success -> _state.update { it.copy(isSaving = false, saved = true) }
                is ApiResult.Error -> _state.update {
                    it.copy(
                        isSaving = false,
                        error = res.message
                    )
                }
            }
        }
    }


//    fun updateLeadDetails(leadId: String, leadData: Lead) {
//        viewModelScope.launch {
//            try {
//                val updatedLead = apiService.updateLeadDetails(leadId, leadData)
//                _lead.value = updatedLead
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
}