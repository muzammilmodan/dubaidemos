package com.trootechdemo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trootechdemo.model.ChatConversationData
import com.trootechdemo.model.ConversationData
import java.lang.reflect.Type
import java.util.*

object ConversationChatDataConverter {

        @TypeConverter
        fun fromString(value: String?): ArrayList<ChatConversationData> {
            val listType: Type = object : TypeToken<ArrayList<ChatConversationData?>?>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<ChatConversationData?>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }

}