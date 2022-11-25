package com.trootechdemo.model

import androidx.room.*
import com.trootechdemo.utils.Constants
import com.trootechdemo.utils.ConversationDataConverter

@Entity(tableName = Constants.TABLE_NAME_CHAT_DETAILS)
data class ConversationListResponse(
    @ColumnInfo(name = "mainid")
    @PrimaryKey(autoGenerate = false)
    val mainid: Int?=0,
    val code: Int?=null,
    @TypeConverters(ConversationDataConverter::class)
    val data: ArrayList<ConversationData>?=null,
    val message: String?=null,
    val requests_count: Int?=null
)


@Entity(tableName = Constants.TABLE_NAME_CHAT_CONVERS)
data class ConversationData(
    val accepted: Int?=null,
    val conversation_created_at: String?=null,
    val conversation_status: Int?=null,
    val created_at: String?=null,
    val from_id: Int?=null,
    val media: String?=null,
    val message_type: String?=null,
    val new_messages: Int?=null,
    val owner: Int?=null,
    val seen: Int?=null,
    val sticker: String?=null,
    val text: String?=null,
    val time: String?=null,
    val to_id: Int?=null,
    @Embedded val user: User?=null,
    @ColumnInfo(name = "convertid")
    @PrimaryKey(autoGenerate = false)
    val id: Int?=0,
)

data class Errors(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val error_id: String,
    val error_text: String
)

@Entity(tableName = Constants.TABLE_NAME_PERSONS)
data class User(
    @ColumnInfo(name = "userid")
    @PrimaryKey(autoGenerate = true)
    val userid: Int?=0,
    val avater: String?=null,
    val first_name: String?=null,
    val fullname: String?=null,
    val last_name: String?=null,
    val lastseen: String?=null,
//    @Relation(parentColumn = "userid", entityColumn = "userid", entity = Mediafile::class)
//    val mediafiles: ArrayList<Mediafile>?=null,
    val username: String?=null,
    val verified_final: Boolean=false
)

//@Entity(tableName = Constants.TABLE_NAME_MEDIA)
//data class Mediafile(
//    @ColumnInfo(name = "mediaid")
//    @PrimaryKey(autoGenerate = true)
//    val Mediaid: Int?=0,
//    val avater: String?=null,
//    val full: String?=null,
//    val is_private: Int=0,
//    val private_avater: String?=null,
//    val private_full: String?=null
//)