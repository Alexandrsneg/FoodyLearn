package com.example.foodylearn.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodylearn.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(Constants.PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(Constants.PREFERENCES_MEAL_TYPE_ID)

        val selectedDietType = stringPreferencesKey(Constants.PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(Constants.PREFERENCES_DIET_TYPE_ID)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        context.dataStore.edit {
            it[PreferenceKeys.selectedMealType] = mealType
            it[PreferenceKeys.selectedMealTypeId] = mealTypeId
            it[PreferenceKeys.selectedDietType] = dietType
            it[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = context.dataStore.data
        .catch {
            if (it is IOException){
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
            val selectedMealType = it[PreferenceKeys.selectedMealType] ?: Constants.DEFAULT_MEAL_TYPE
            val selectedMealTypeId = it[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = it[PreferenceKeys.selectedDietType] ?: Constants.DEFAULT_DIET_TYPE
            val selectedDietTypeId = it[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(selectedMealType, selectedMealTypeId, selectedDietType, selectedDietTypeId)
        }


}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int,
){
}