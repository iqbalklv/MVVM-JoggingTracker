package com.miqbalkalevi.joggingtracker.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "jog_table")
@Parcelize

data class Jog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val img: Bitmap? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val avgSpeedInKMH: Float = 0f,
    val distanceInMeters: Int = 0,
    val duration: Long = 0L,
    val caloriesBurned: Int = 0
) : Parcelable {
    val timestampFormatted: String
        get() = DateFormat.getDateInstance(DateFormat.FULL).format(timestamp)
}