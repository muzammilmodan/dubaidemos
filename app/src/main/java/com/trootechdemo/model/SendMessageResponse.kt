package com.trootechdemo.model

data class SendMessageResponse(
    val `data`: Data,
    val errors: SendMessageErrors,
    val hash_id: String,
    val message: String,
    val status: Int
)

data class Data(
    val created_at: String,
    val from: Int,
    val from_delete: Int,
    val id: Int,
    val media: String,
    val message_type: String,
    val seen: Int,
    val sticker: String,
    val text: String,
    val to: Int,
    val to_delete: Int
)

data class SendMessageErrors(
    val error_id: String,
    val error_text: String
)