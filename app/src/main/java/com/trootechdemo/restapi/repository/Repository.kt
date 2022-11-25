package com.trootechdemo.restapi.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.trootechdemo.model.ChatConversation
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.model.SendMessageResponse
import com.trootechdemo.restapi.api.ApiCallback
import com.trootechdemo.restapi.api.BaseApiResponse
import com.trootechdemo.restapi.remote.RemoteDataSource
import com.trootechdemo.room.ChatDao
import com.trootechdemo.room.ChatDatabase
import com.trootechdemo.utils.Utils.Companion.subscribeOnBackground
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//Repository connect with ViewModel, So any action thru return values so suspend method provide details in ViewModel.
@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    application: Application
) : BaseApiResponse() {

    suspend fun callGetConversationListApi(limit: Int,offset: Int,api_token_values:String):
            Flow<ApiCallback<ConversationListResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.callGetConversationList(limit,offset,api_token_values) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun callGetChatConversationList(limit: Int,api_key_values:String,to_userid: Int,is_unread: Int)
    : Flow<ApiCallback<ChatConversation>> {
        return flow {
            emit(safeApiCall { remoteDataSource.callGetChatConversationList(
                limit,api_key_values,to_userid,is_unread) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun callSentChatMessageApi(api_key_values:String,to_userid: Int, message: String)
            : Flow<ApiCallback<SendMessageResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.callSentChatMessage(api_key_values,to_userid,message) })
        }.flowOn(Dispatchers.IO)
    }

    private var personDao: ChatDao
    private var allPersons: LiveData<List<ConversationListResponse>>
    private val database = ChatDatabase.getInstance(application)

    init {
        personDao = database.personDao() //initialization of dao class
        allPersons = personDao.getAllUsers()
    }


    //Insert data in database using insert method. this method connect to Table
    fun insert(note: ConversationListResponse) {
        subscribeOnBackground {
            personDao.insertPersonData(note)
        }
    }
    fun getAllPerson(): LiveData<List<ConversationListResponse>> {
        return allPersons
    }

    fun update(note: ConversationListResponse) {
        subscribeOnBackground {
            personDao.updatePersonData(note)
        }
    }

    fun delete(note: ConversationListResponse) {
        subscribeOnBackground {
            personDao.deletePersonData(note)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            personDao.deleteAllUser()
        }
    }
}

