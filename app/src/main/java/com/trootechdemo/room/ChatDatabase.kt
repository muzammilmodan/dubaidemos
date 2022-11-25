package com.trootechdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trootechdemo.model.ConversationListResponse
import com.trootechdemo.utils.Constants
import com.trootechdemo.utils.ConvertersByte


@Database(entities = [ConversationListResponse::class],
    version = Constants.DATABASE_VERSION,exportSchema = false)
@TypeConverters(ConvertersByte::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun personDao(): ChatDao

    companion object {
        @Volatile var instance: ChatDatabase? = null

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