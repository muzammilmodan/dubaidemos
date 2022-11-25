package com.trootechdemo.restapi


//Constant request values stored, All Api regarding URL, request and response param managed here.
object Apis {

    const val BASE_URL = " http://dubae.vip/endpoint/v1/asd/"

    //Api Url
    const val GET_CONVERSATION_LIST = "messages/get_conversation_list"
    const val GET_CHAT_CONVERSATION_LIST = "messages/get_chat_conversations"
    const val SEND_TEXT_MESSAGE ="messages/send_text_message"

    //Request param details
    const val REQUEST_LIMIT = "limit"
    const val REQUEST_OFFSET = "offset"
    const val REQUEST_ACCESS_TOKEN = "access_token"
    const val REQUEST_TO_USER_ID="to_userid"
    const val REQUEST_IS_UNREAD="is_unread"
    const val REQUEST_MESSAGE="message"
}

