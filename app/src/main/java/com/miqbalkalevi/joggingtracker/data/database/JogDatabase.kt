package com.miqbalkalevi.joggingtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miqbalkalevi.joggingtracker.data.model.Jog

@Database(entities = [Jog::class], version = 1)
@TypeConverters(Converters::class)
abstract class JogDatabase : RoomDatabase() {

    abstract fun getJogDao(): JogDao
}