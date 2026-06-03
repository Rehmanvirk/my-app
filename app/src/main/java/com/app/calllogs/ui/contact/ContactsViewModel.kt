package com.app.calllogs.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.database.AllContactEntity
import com.app.calllogs.database.ContactsDao
import com.app.calllogs.di.data.Contacts
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.leads.LeadsUiState
import com.app.calllogs.ui.nav.BottomRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repo: ContactsRepository,
    private val userPreference: UserPreference,
    private val contactDao: ContactsDao
) : ViewModel() {

    private val _state = MutableStateFlow(ContactsUiState(isLoading = true))
    val state: StateFlow<ContactsUiState> = _state

    init {
        getLeads()
    }
    fun onQueryChange(v: String) = _state.update { it.copy(query = v) }
//    fun onStatusChange(s: LeadStatus) = _state.update { it.copy(selectedStatus = s) }

    fun getLeads() {

        val id = userPreference.getUserPreference()?.user?.id?:""
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }

            when (val res = repo.getContacts(id)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, allContacts = res.data.contacts?:emptyList()) }

                    res.data.contacts?.let {
                        contactDao.delAll("Contact")
                        for (l in it){
                            val con = AllContactEntity(id = 0,_cid = l._id, name = l.Full_Name?:"", number = l.Phone?:"", type = "Contact", note = "")
                            contactDao.insert(con)
                        }
                    }

                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }
}



data class ContactsUiState(
    val query: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val allContacts: List<Contacts> = emptyList(),

    ) {
    val filtered: List<Contacts>
        get() {
            val q = query.trim().lowercase()
            return allContacts
//                .filter { selectedStatus == LeadStatus.ALL || it.dynamicFields.Lead_Status == selectedStatus.name }
                .filter {
                    q.isEmpty() || it.First_Name.lowercase().contains(q)
                }
        }
}