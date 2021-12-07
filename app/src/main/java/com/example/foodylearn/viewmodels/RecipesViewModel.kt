package com.example.foodylearn.viewmodels

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
    var isNetworkStatusAvailable = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    var readBackOnline = dataStoreRepository.readBackOnline

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }
    }

    fun saveBackOnline(isBackOnline: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(isBackOnline)
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
        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        search?.let { queries[Constants.QUERY_SEARCH] = it }
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        queries[Constants.QUERY_FILL_INGRERDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if(!isNetworkStatusAvailable){
            Toast.makeText(getApplication(), "No internet connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        }
        else {
            if (backOnline) {
                Toast.makeText(getApplication(), "Connection fixed", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }

    }

}