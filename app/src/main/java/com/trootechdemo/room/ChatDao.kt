package com.trootechdemo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.utils.Constants

@Dao
interface ChatDao {

    @Insert
    fun insertPersonData(user: ConversationListResponse)

    @Query("Select * from ${Constants.TABLE_NAME_CHAT_DETAILS}")
    fun getAllUsers() : LiveData<List<ConversationListResponse>>


    @Update
    fun updatePersonData(user:ConversationListResponse)

    @Delete
    fun deletePersonData(user:ConversationListResponse)

    @Query("delete from ${Constants.TABLE_NAME_CHAT_DETAILS}")
    fun deleteAllUser()


}