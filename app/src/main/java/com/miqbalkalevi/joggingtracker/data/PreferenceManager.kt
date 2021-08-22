package com.miqbalkalevi.joggingtracker.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferenceManager"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "main_preferences")

data class FilterPreferences(val sortOrder: String)
data class UserData(val weight: Int, val height: Int)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val filterPreferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            //region initializing default preferences values
            val sortOrder = prefs[preferencesKeys.JOG_SORT_ORDER] ?: SORT_BY_DATE.stringValue
            //endregion

            //returns a flow of FilterPreferences
            FilterPreferences(sortOrder)
        }

    suspend fun updateSortOrder(jogSortOrder: JogSortOrder) {
        dataStore.edit { prefs ->
            prefs[preferencesKeys.JOG_SORT_ORDER] = jogSortOrder.stringValue
        }
    }

    suspend fun updateUserData(weight: Int, height: Int) {
        dataStore.edit { prefs ->
            prefs[preferencesKeys.USER_DATA_WEIGHT] = weight
            prefs[preferencesKeys.USER_DATA_HEIGHT] = height
        }
    }

    suspend fun readUserData(): UserData? {
        val weight = dataStore.data.first()[preferencesKeys.USER_DATA_WEIGHT] ?: USER_DATA_NULL
        val height = dataStore.data.first()[preferencesKeys.USER_DATA_HEIGHT] ?: USER_DATA_NULL
        Log.d("USERDATATEST", "$weight W|$height H")
        val userData =
            if (weight == USER_DATA_NULL ||
                height == USER_DATA_NULL
            ) {
                null
            } else {
                UserData(weight, height)
            }
        return userData
    }


    private object preferencesKeys {
        val JOG_SORT_ORDER = stringPreferencesKey("jog_sort_order")
        val USER_DATA_WEIGHT = intPreferencesKey("user_data_weight")
        val USER_DATA_HEIGHT = intPreferencesKey("user_data_height")
    }

    companion object {
        val SORT_BY_DATE =
            JogSortOrder(JogSortOrder.Type.SORT_BY_DATE)
        val SORT_BY_AVERAGE_SPEED =
            JogSortOrder(JogSortOrder.Type.SORT_BY_AVERAGE_SPEED)
        val SORT_BY_DURATION = JogSortOrder(JogSortOrder.Type.SORT_BY_DURATION)
        val SORT_BY_CALORIES_BURNED =
            JogSortOrder(JogSortOrder.Type.SORT_BY_CALORIES_BURNED)
        val SORT_BY_DISTANCE = JogSortOrder(JogSortOrder.Type.SORT_BY_DISTANCE)

        val USER_DATA_NULL = -1
    }
}

class JogSortOrder(
    val type: Type,
    val stringValue: String =
        when (type) {
            //Setup type and its string value here.
            Type.SORT_BY_DATE -> StringValue.SORT_BY_DATE
            Type.SORT_BY_AVERAGE_SPEED -> StringValue.SORT_BY_AVERAGE_SPEED
            Type.SORT_BY_DURATION -> StringValue.SORT_BY_DURATION
            Type.SORT_BY_CALORIES_BURNED -> StringValue.SORT_BY_CALORIES_BURNED
            Type.SORT_BY_DISTANCE -> StringValue.SORT_BY_DISTANCE
        }
) {

    //Used in DB queries. Must be constant.
    class StringValue {
        companion object {
            const val SORT_BY_DATE = "sort_by_date"
            const val SORT_BY_AVERAGE_SPEED = "sort_by_average_speed"
            const val SORT_BY_DURATION = "sort_by_duration"
            const val SORT_BY_CALORIES_BURNED = "sort_by_calories_burned"
            const val SORT_BY_DISTANCE = "sort_by_distance"
        }
    }

    enum class Type {
        SORT_BY_DATE,
        SORT_BY_AVERAGE_SPEED,
        SORT_BY_DURATION,
        SORT_BY_CALORIES_BURNED,
        SORT_BY_DISTANCE;
    }
}

