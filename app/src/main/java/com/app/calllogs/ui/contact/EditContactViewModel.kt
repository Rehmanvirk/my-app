package com.app.calllogs.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.di.data.ContactDetailsResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ApiInterface
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.LeadsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditContactViewModel @Inject constructor(
    private val repo: ContactsRepository
) : ViewModel() {

    private val _contact = MutableStateFlow<ContactDetailsResponse?>(null)
    val contact: StateFlow<ContactDetailsResponse?> = _contact

    fun getContactDetails(leadId: String) {
        viewModelScope.launch {
            val res = repo.getContactDetails(leadId)
            when (res) {
                is ApiResult.Success -> _contact.value = res.data
                is ApiResult.Error -> _contact.value = ContactDetailsResponse(message = "no data found")
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