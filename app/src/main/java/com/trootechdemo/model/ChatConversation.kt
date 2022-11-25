package com.trootechdemo.model

data class ChatConversation(
    val code: Int,
    val `data`: ArrayList<ChatConversationData>,
    val errors: ChatConversationErrors,
    val message: String
)

data class ChatConversationData(
    val created_at: Int,
    val from: Int,
    val from_avater: String,
    val from_delete: Int,
    val from_name: String,
    val id: Int,
    val media: String,
    val message_type: String,
    val seen: Int,
    val sticker: String,
    val text: String,
    val to: Int,
    val to_avater: String,
    val to_delete: Int,
    val to_name: String,
    val type: String
)

data class ChatConversationErrors(
    val error_id: String,
    val error_text: String
)