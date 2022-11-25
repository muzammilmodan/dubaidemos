package com.trootechdemo.ui.chat


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.trootechdemo.model.*

import com.trootechdemo.restapi.api.ApiCallback
import com.trootechdemo.restapi.repository.Repository


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _responseMutableGetCategory: MutableLiveData<ApiCallback<ConversationListResponse>>
    = MutableLiveData()
    val responseGetCategoryMain: LiveData<ApiCallback<ConversationListResponse>>
    = _responseMutableGetCategory

    fun fetchCategoryListResponse(limit: Int,offset: Int,api_token_values:String)
    = viewModelScope.launch {
        repository.callGetConversationListApi(limit,offset,api_token_values).collect { values ->
            _responseMutableGetCategory.value = values
        }
    }

    private val _responseMutableGetChatConversation: MutableLiveData<ApiCallback<ChatConversation>>
            = MutableLiveData()
    val responseGetChatConversation: LiveData<ApiCallback<ChatConversation>>
            = _responseMutableGetChatConversation

    fun fetchCallGetChatConversationList(limit: Int,api_key_values:String,to_userid: Int,is_unread: Int)
            = viewModelScope.launch {
        repository.callGetChatConversationList(limit,api_key_values,to_userid,is_unread).collect { values ->
            _responseMutableGetChatConversation.value = values
        }
    }

    private val _responseMutableSendMessageResponse: MutableLiveData<ApiCallback<SendMessageResponse>>
            = MutableLiveData()
    val responseSendMessageResponse: LiveData<ApiCallback<SendMessageResponse>>
            = _responseMutableSendMessageResponse

    fun fetchSendMessageResponseApi(api_key_values:String,to_userid: Int, message: String)
            = viewModelScope.launch {
        repository.callSentChatMessageApi(api_key_values,to_userid,message).collect { values ->
            _responseMutableSendMessageResponse.value = values
        }
    }

    //Room
    private val allUsers = repository.getAllPerson()

    fun insert(note: ConversationListResponse) {
        repository.insert(note)
    }

    fun delete(note: ConversationListResponse) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<ArrayList<ConversationListResponse>> {
        return allUsers
    }

}