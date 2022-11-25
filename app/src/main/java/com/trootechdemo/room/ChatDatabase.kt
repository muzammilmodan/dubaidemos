package com.trootechdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trootechdemo.model.ChatConversation
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.utils.Constants
import com.trootechdemo.utils.ConversationChatDataConverter
import com.trootechdemo.utils.ConversationDataConverter
import com.trootechdemo.utils.ConvertersByte

/*
* Insert here as per create table name. means above data class cre
* */
@Database(entities = [ConversationListResponse::class, ChatConversation::class],
    version = Constants.DATABASE_VERSION,exportSchema = false)
@TypeConverters(ConvertersByte::class, ConversationDataConverter::class,
    ConversationChatDataConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun personDao(): ChatDao

    companion object {
        @Volatile var instance: ChatDatabase? = null

        /*Create database and instance create here*/

        //Return database values
        @Synchronized
        fun getInstance(ctx: Context): ChatDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, ChatDatabase::class.java,
                    Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }

    }


}