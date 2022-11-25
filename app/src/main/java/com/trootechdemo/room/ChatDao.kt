package com.trootechdemo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trootechdemo.model.ChatConversation
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.utils.Constants

@Dao
interface ChatDao {

    //Chat User list
    @Insert
    fun insertPersonData(user: ConversationListResponse)

    @Query("Select * from ${Constants.TABLE_NAME_CHAT_DETAILS}")
    fun getAllUsers() : LiveData<List<ConversationListResponse>>

    @Query("delete from ${Constants.TABLE_NAME_CHAT_DETAILS}")
    fun deleteAllUser()

    //Message list
    @Insert
    fun insertMessageData(user: ChatConversation)

    @Query("Select * from ${Constants.TABLE_NAME_CHAT_MESSAGE_DTL}")
    fun getAllMessage() : LiveData<List<ChatConversation>>

    @Query("delete from ${Constants.TABLE_NAME_CHAT_MESSAGE_DTL}")
    fun deleteAllMessage()







}