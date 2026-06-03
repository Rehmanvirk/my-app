package com.app.calllogs.ui.deals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.database.AllContactEntity
import com.app.calllogs.database.ContactsDao
import com.app.calllogs.di.data.ContactDetailsResponse
import com.app.calllogs.di.data.Contacts
import com.app.calllogs.di.data.Deal
import com.app.calllogs.di.data.DealDetailResponse
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.DealsRepository
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.contact.ContactsUiState
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
class DealsViewModel @Inject constructor(
    private val repo: DealsRepository,
    private val userPreference: UserPreference,
    private val contactDao: ContactsDao
) : ViewModel() {

    private val _state = MutableStateFlow(DealsUiState(isLoading = true))
    val state: StateFlow<DealsUiState> = _state

    private val _deal = MutableStateFlow<DealDetailResponse?>(null)
    val deal: StateFlow<DealDetailResponse?> = _deal

    init {
        getLeads()
    }
    fun onQueryChange(v: String) = _state.update { it.copy(query = v) }
//    fun onStatusChange(s: LeadStatus) = _state.update { it.copy(selectedStatus = s) }

    fun getLeads() {
        val id = userPreference.getUserPreference()?.user?.id?:""
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }

            when (val res = repo.getDeals()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, allDeals = res.data.deals?:emptyList()) }
                    res.data.deals?.let {
                        contactDao.delAll("Deal")
                        for (l in it){
                            val con = AllContactEntity(id = 0,_cid = l._id, name = l.Deal_Name?:"", number = l.Amount?:"", type = "Deal", note = "")
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

    fun getDealDetails(id: String) {
        viewModelScope.launch {
            val res = repo.getDealDetail(id)
            when (res) {
                is ApiResult.Success -> _deal.value = res.data
                is ApiResult.Error -> _deal.value = DealDetailResponse()
            }
        }
    }
}



data class DealsUiState(
    val query: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val allDeals: List<Deal> = emptyList(),

    ) {
    val filtered: List<Deal>
        get() {
            val q = query.trim().lowercase()
            return allDeals
//                .filter { selectedStatus == LeadStatus.ALL || it.dynamicFields.Lead_Status == selectedStatus.name }
                .filter {
                    q.isEmpty() || it.Stage.orEmpty().lowercase().contains(q)
                }
        }
}