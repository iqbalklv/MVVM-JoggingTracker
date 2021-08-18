package com.miqbalkalevi.joggingtracker.data.database

import androidx.room.*
import com.miqbalkalevi.joggingtracker.data.SortOrder
import com.miqbalkalevi.joggingtracker.data.model.Jog
import kotlinx.coroutines.flow.Flow

@Dao
interface JogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJog(jog: Jog)

    @Delete
    suspend fun deleteJog(jog: Jog)

    @Query(
        """
        SELECT * FROM jog_table
        ORDER BY 
        CASE WHEN :sortOrder = '${SortOrder.stringValue.SORT_BY_DATE}' THEN timestamp END DESC,
        CASE WHEN :sortOrder = '${SortOrder.stringValue.SORT_BY_DURATION}' THEN duration END DESC,
        CASE WHEN :sortOrder = '${SortOrder.stringValue.SORT_BY_CALORIES_BURNED}' THEN caloriesBurned END DESC,
        CASE WHEN :sortOrder = '${SortOrder.stringValue.SORT_BY_AVERAGE_SPEED}'  THEN avgSpeedInKMH END DESC,
        CASE WHEN :sortOrder = '${SortOrder.stringValue.SORT_BY_DISTANCE}' THEN distanceInMeters END DESC
    """
    )
    fun getJogsSortedBy(sortOrder: String): Flow<List<Jog>>

    @Query("SELECT SUM(duration) FROM jog_table")
    fun getTotalDuration(): Flow<Long>

    @Query("SELECT SUM(caloriesBurned) FROM jog_table")
    fun getTotalCaloriesBurned(): Flow<Int>

    @Query("SELECT SUM(distanceInMeters) FROM jog_table")
    fun getTotalDistance(): Flow<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM jog_table")
    fun getTotalAverageSpeed(): Flow<Float>
}