package com.trootechdemo.restapi.remote

import com.trootechdemo.restapi.api.ApiServices
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiServices) {

    suspend fun callGetConversationList(limit: Int,offset: Int,api_token_values:String)
    = apiService.getConversationList(limit,offset,api_token_values)

    suspend fun callGetChatConversationList(limit: Int,api_key_values:String,to_userid: Int,is_unread: Int)
            = apiService.getChatConversationList(limit,api_key_values,to_userid,is_unread)

    suspend fun callSentChatMessage(api_key_values:String,to_userid: Int, message: String)
            = apiService.sentChatMessage(api_key_values,to_userid,message)
}