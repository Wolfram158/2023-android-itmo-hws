package ru.ok.itmo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChannels(item: String)

    //@Query("SELECT * FROM channels")
    //fun getAllChannels(): Flow<List<ChannelItem>>
}