package com.app.calllogs.ui.chat

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.calllogs.di.data.Contacts
import com.app.calllogs.di.data.Conversations
import com.app.calllogs.di.data.Deal
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.data.Messages
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.ChatRepository
import com.app.calllogs.di.repository.ContactsRepository
import com.app.calllogs.di.repository.DealsRepository
import com.app.calllogs.di.repository.LeadsRepository
import com.app.calllogs.storage.UserPreference
import com.app.calllogs.ui.contact.ContactsUiState
import com.app.calllogs.ui.deals.DealsUiState
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
class ChatViewModel @Inject constructor(
    private val repo: ChatRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _state = MutableStateFlow(ChatsUiState(isLoading = true))
    val state: StateFlow<ChatsUiState> = _state

    init {
        getConversations()
    }
    fun onQueryChange(v: String) = _state.update { it.copy(query = v) }
//    fun onStatusChange(s: LeadStatus) = _state.update { it.copy(selectedStatus = s) }

    fun getConversations() {
        val id = userPreference.getUserPreference()?.user?.id?:""
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }

            when (val res = repo.getConversations()) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, allChats = res.data.conversations?:emptyList()) }

                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getMessagesList(id : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }

            when (val res = repo.getMessagesList(id)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, allMessages = res.data.messages?:emptyList()) }

                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun sendMessage(id : String,text : String,conversationId : String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = "null") }

            when (val res = repo.sendMessage(id,text)) {
                is ApiResult.Success -> {
                    getMessagesList(conversationId)
//                    _state.update { it.copy(isLoading = false, allMessages = res.data.messages?:emptyList()) }

                }

                is ApiResult.Error -> {
                    _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun getIsMe(id : String): Boolean{
        return id==userPreference.getUserPreference()?.user?.id
    }

    fun getId(): String{
        return userPreference.getUserPreference()?.user?.id?:""
    }
}



data class ChatsUiState(
    val query: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val allChats: List<Conversations> = emptyList(),
    val allMessages: List<Messages> = emptyList(),
    ) {
//    val filtered: List<Deal>
//        get() {
//            val q = query.trim().lowercase()
//            return allDeals
////                .filter { selectedStatus == LeadStatus.ALL || it.dynamicFields.Lead_Status == selectedStatus.name }
//                .filter {
//                    q.isEmpty() || it.Stage.orEmpty().lowercase().contains(q)
//                }
//        }
}