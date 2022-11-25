package com.trootechdemo.restapi.api

import com.trootechdemo.model.ChatConversation
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.model.SendMessageResponse
import com.trootechdemo.restapi.Apis
import retrofit2.Response
import retrofit2.http.*

/**
 * Stored all network regarding call, using suspend method thru handle API call.
 **/
interface ApiServices {

    @FormUrlEncoded
    @POST(Apis.GET_CONVERSATION_LIST)
    suspend fun getConversationList(
        @Field(Apis.REQUEST_LIMIT) limit: Int?,
        @Field(Apis.REQUEST_OFFSET) offset: Int?,
        @Field(Apis.REQUEST_ACCESS_TOKEN) access_token: String?
    ): Response<ConversationListResponse>

    @FormUrlEncoded
    @POST(Apis.GET_CHAT_CONVERSATION_LIST)
    suspend fun getChatConversationList(
        @Field(Apis.REQUEST_LIMIT) limit: Int?,
        @Field(Apis.REQUEST_ACCESS_TOKEN) access_token: String?,
        @Field(Apis.REQUEST_TO_USER_ID) to_userid: Int?,
        @Field(Apis.REQUEST_IS_UNREAD) is_unread: Int?
    ): Response<ChatConversation>


    @FormUrlEncoded
    @POST(Apis.SEND_TEXT_MESSAGE)
    suspend fun sentChatMessage(
        @Field(Apis.REQUEST_ACCESS_TOKEN) access_token: String?,
        @Field(Apis.REQUEST_TO_USER_ID) to_userid: Int?,
        @Field(Apis.REQUEST_MESSAGE) message: String?
    ): Response<SendMessageResponse>
}