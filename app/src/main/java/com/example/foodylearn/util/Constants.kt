package com.example.foodylearn.util

object Constants {

    const val API_KEY = "b0414841b9bd4c19ac60ce69cd737f97"
    const val BASE_URL = "https://api.spoonacular.com"
    const val BASE_IMAGES_URL = "https://spoonacular.com/recipeImages/"

    // API Queri keys

    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_ADD_RECIPE_INFO = "addRecipeInformation"
    const val QUERY_FILL_INGRERDIENTS = "fillIngredients"
    const val QUERY_SEARCH = "query"

    //ROOM Database

    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"
    const val FAVORITES_TABLE = "favorites_table"
    const val JOKE_TABLE = "joke_table"

    //BottomSheet and prefs
    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val DEFAULT_RECIPES_NUMBER = "50"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCES_NAME = "foody_preferences"
    const val PREFERENCES_BACK_ONLINE = "backOnline"

    const val RECIPE_RESULT_KEY = "recipeBundle"


}