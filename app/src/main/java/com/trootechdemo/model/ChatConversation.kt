package com.trootechdemo.model

import androidx.room.*
import com.trootechdemo.utils.Constants
import com.trootechdemo.utils.ConversationChatDataConverter


@Entity(tableName = Constants.TABLE_NAME_CHAT_MESSAGE_DTL)
data class ChatConversation(
    @ColumnInfo(name = "chatid")
    @PrimaryKey(autoGenerate = false)
    val mainid: Int?=0,
    val code: Int,
    @TypeConverters(ConversationChatDataConverter::class)
    val `data`: ArrayList<ChatConversationData>,
    @Embedded val errors: ChatConversationErrors,
    val message: String
)


@Entity(tableName = Constants.TABLE_NAME_CHAT_MESSAGE)
data class ChatConversationData(
    @ColumnInfo(name = "msgid")
    @PrimaryKey(autoGenerate = false)
    val id: Int?=0,
    val created_at: Int,
    val from: Int,
    val from_avater: String,
    val from_delete: Int,
    val from_name: String,
    val media: String,
    val message_type: String,
    val seen: Int,
    val sticker: String,
    val text: String,
    val to: Int,
    val to_avater: String,
    val to_delete: Int,
    val to_name: String,
    val type: String,
    @Embedded val user: User?=null,
)

data class ChatConversationErrors(
    val error_id: String,
    val error_text: String
)