package com.app.calllogs.ui.leads

import android.util.Printer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.database.AllContactEntity
import com.app.calllogs.database.AppDatabase
import com.app.calllogs.database.ContactsDao
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.storage.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlin.collections.filter

@HiltViewModel
class LeadsViewModel @Inject constructor(
    private val repo: LeadsRepository,
    private val userPreference: UserPreference,
    private val contactDao: ContactsDao
) : ViewModel() {

    private val _state = MutableStateFlow(LeadsUiState(isLoading = true))
    val state: StateFlow<LeadsUiState> = _state

    init {
        getLeads()
    }

    fun onQueryChange(v: String) = _state.update { it.copy(query = v) }
    fun onStatusChange(s: LeadStatus) = _state.update { it.copy(selectedStatus = s) }

    fun getLeads() {

        val id = userPreference.getUserPreference()?.user?.id ?: ""
        viewModelScope.launch {
            delay(500)
            _state.update {
                it.copy(isLoading = true, error = "null", allLeads = emptyList())
            }

            when (val res = repo.getLeads(id)) {
                is ApiResult.Success -> {

                    _state.update {
                        it.copy(
                            isLoading = false,
                            allLeads = res.data.leads ?: emptyList(),
                        )
                    }
                    _state.value.filtered = res.data.leads ?: emptyList()
                    res.data.leads?.let {
                        contactDao.delAll("Lead")
                        for (l in it){
                            val con = AllContactEntity(id = 0,_cid = l._id, name = l.dynamicFields.fullName?:"", number = l.dynamicFields.phone?:"", type = "Lead", note = "")
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


data class LeadsUiState(
    val query: String = "",
    val error: String = "",
    val selectedStatus: LeadStatus = LeadStatus.ALL,
    val isLoading: Boolean = false,
    val allLeads: List<Lead> = emptyList(),

    ) {
    var filtered: List<Lead>
        get() {
            val q = query.trim().lowercase()
            return allLeads
                .filter { selectedStatus == LeadStatus.ALL  ||
                        it.dynamicFields.leadStatus.orEmpty().trim().replace(" ","").contains(
                    selectedStatus.name) }
                .filter {
                    q.isEmpty() || it.dynamicFields.fullName.toString().lowercase()
                        .contains(q) || it.dynamicFields.leadStatus.orEmpty().lowercase()
                        .contains(q)
                }
        }
        set(value) {}
}