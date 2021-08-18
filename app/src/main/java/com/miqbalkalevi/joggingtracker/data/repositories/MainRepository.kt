package com.miqbalkalevi.joggingtracker.data.repositories

import com.miqbalkalevi.joggingtracker.data.database.JogDao
import com.miqbalkalevi.joggingtracker.data.model.Jog
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val jogDao: JogDao
) {
    suspend fun insertJog(jog: Jog) = jogDao.insertJog(jog)
    suspend fun deleteJog(jog: Jog) = jogDao.deleteJog(jog)

    fun getJogsSortedBy(sortOrder: String) = jogDao.getJogsSortedBy(sortOrder)

    fun getTotalDuration() = jogDao.getTotalDuration()
    fun getTotalCaloriesBurned() = jogDao.getTotalCaloriesBurned()
    fun getTotalDistance() = jogDao.getTotalDistance()
    fun getTotalAverageSpeed() = jogDao.getTotalAverageSpeed()
}