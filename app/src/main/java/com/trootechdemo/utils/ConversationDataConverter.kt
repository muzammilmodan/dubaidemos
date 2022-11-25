package com.trootechdemo.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trootechdemo.model.ConversationData
import java.lang.reflect.Type
import java.util.*

object ConversationDataConverter {

        @TypeConverter
        fun fromString(value: String?): ArrayList<ConversationData> {
            val listType: Type = object : TypeToken<ArrayList<ConversationData?>?>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<ConversationData?>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }
//
//    @TypeConverter
//    fun toData(data: String?): List<ConversationData?>? {
//        val gson = Gson()
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType = object : TypeToken<List<ConversationData?>?>() {}.type
//        return gson.fromJson<List<ConversationData?>>(data, listType)
//    }
//
//    @TypeConverter
//    fun toCategories(value: String?): ConversationData {
//        if (value == null || value.isEmpty()) {
//            return ConversationData()
//        }
//
//        val list: List<String> = value.split(",")
//        val longList = ArrayList<Long>()
//        for (item in list) {
//            if (!item.isEmpty()) {
//                longList.add(item.toLong())
//            }
//        }
//        return ConversationData(longList)
//    }
//
//    @TypeConverter
//    fun toString(categories: ConversationData?): String {
//
//        var string = ""
//
//        if (categories == null) {
//            return string
//        }
//
//        categories.categories.forEach {
//            string += "$it,"
//        }
//        return string
//    }
}