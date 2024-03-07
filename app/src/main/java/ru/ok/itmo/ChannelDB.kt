package ru.ok.itmo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChannelItem::class], version = 1)
abstract class ChannelDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        var instance: ChannelDB? = null
        private var LOCK = Any()

        operator fun invoke(context: Context): ChannelDB? {
            if (instance != null) {
                Log.e("INVOKE", "INVOKE")
                return instance
            }
            synchronized(LOCK) {
                getDB(context).also {
                    instance = it
                }
            }
            return instance
        }
        fun getDB(context: Context): ChannelDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ChannelDB::class.java,
                "channels.db"
            ).build()
        }
    }
}