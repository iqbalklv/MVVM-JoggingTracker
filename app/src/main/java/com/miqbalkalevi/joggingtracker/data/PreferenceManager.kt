package com.miqbalkalevi.joggingtracker.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferenceManager"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class FilterPreferences(val sortOrder: String)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val preferencesFlow = context.dataStore.data
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
            val sortOrder = prefs[preferencesKeys.SORT_ORDER] ?: SortOrder.stringValue.SORT_BY_DATE

            //endregion

            //returns a flow of FilterPreferences
            FilterPreferences(sortOrder)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { prefs ->
            prefs[preferencesKeys.SORT_ORDER] =
                when (sortOrder) {
                    SortOrder.SORT_BY_DATE -> SortOrder.stringValue.SORT_BY_DATE
                    SortOrder.SORT_BY_AVERAGE_SPEED -> SortOrder.stringValue.SORT_BY_AVERAGE_SPEED
                    SortOrder.SORT_BY_DURATION -> SortOrder.stringValue.SORT_BY_DURATION
                    SortOrder.SORT_BY_CALORIES_BURNED -> SortOrder.stringValue.SORT_BY_CALORIES_BURNED
                    SortOrder.SORT_BY_DISTANCE -> SortOrder.stringValue.SORT_BY_DISTANCE
                }
        }
    }

    private object preferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")

    }
}

enum class SortOrder {
    SORT_BY_DATE,
    SORT_BY_AVERAGE_SPEED,
    SORT_BY_DURATION,
    SORT_BY_CALORIES_BURNED,
    SORT_BY_DISTANCE;

    object stringValue {
        const val SORT_BY_DATE = "sort_by_date"
        const val SORT_BY_AVERAGE_SPEED = "sort_by_average_speed"
        const val SORT_BY_DURATION = "sort_by_duration"
        const val SORT_BY_CALORIES_BURNED = "sort_by_calories_burned"
        const val SORT_BY_DISTANCE = "sort_by_distance"
    }
}