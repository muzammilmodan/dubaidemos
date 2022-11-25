package com.trootechdemo.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trootechdemo.model.ConversationData
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


object ConvertersByte {
    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    fun getBytesToUri(bytesArray:ByteArray) : Uri {
        val s = String(bytesArray)
        return Uri.parse(s)
    }

    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromListToString(list: List<*>): String {
        val type = object: TypeToken<List<*>>() {}.type
        return Gson().toJson(list, type)
    }



//    @TypeConverter
//    fun toData(data: String?): List<ConversationData?>? {
//        val gson = Gson()
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType = object : TypeToken<List<ConversationData?>?>() {}.type
//        return gson.fromJson<List<ConversationData?>>(data, listType)
//    }


}