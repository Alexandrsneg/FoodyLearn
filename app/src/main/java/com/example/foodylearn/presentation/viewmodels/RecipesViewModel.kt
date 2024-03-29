package com.example.foodylearn.presentation.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodylearn.data.DataStoreRepository
import com.example.foodylearn.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(application) {

    private var mealType = Constants.DEFAULT_MEAL_TYPE
    private var dietType = Constants.DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }
    }

    fun applyQueries(search: String? = null): HashMap<String, String> {
        viewModelScope.launch {
            readMealAndDietType.collect {
                mealType = it.selectedMealType
                dietType = it.selectedDietType
            }
        }

        val queries: HashMap<String, String> = HashMap()
        return queries.apply {
            search?.let { put(Constants.QUERY_SEARCH, it) }
            put(Constants.QUERY_NUMBER, Constants.DEFAULT_RECIPES_NUMBER)
            put(Constants.QUERY_TYPE, mealType)
            put(Constants.QUERY_DIET, dietType)
            put(Constants.QUERY_ADD_RECIPE_INFO, "true")
            put(Constants.QUERY_FILL_INGRERDIENTS, "true")
        }
    }
}
