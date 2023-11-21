package com.example.foodylearn.data

import com.example.domain.models.ExtendedIngredientRest
import com.example.domain.models.FoodRecipesRest
import com.example.domain.models.RecipeRest
import com.example.foodylearn.data.database.recipes.RecipesEntity
import com.example.foodylearn.data.models.ExtendedIngredient
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.data.models.Recipe


//******************** UI to REST ******************************
fun FoodRecipesRest.toFoodRecipes() = FoodRecipes(recipes = results.map { it.toRecipe() })
fun RecipeRest.toRecipe() =
    Recipe(
        aggregateLikes = aggregateLikes,
        cheap = cheap,
        dairyFree = dairyFree,
        extendedIngredients = extendedIngredients?.map { it.toExtendedIngredient() },
        glutenFree = glutenFree,
        id = id,
        image = image,
        readyInMinutes = readyInMinutes,
        servings = servings,
        sourceName = sourceName,
        spoonacularSourceUrl = spoonacularSourceUrl,
        summary = summary,
        title = title,
        vegan = vegan,
        vegetarian = vegetarian,
        veryHealthy = veryHealthy,
        weightWatcherSmartPoints = weightWatcherSmartPoints
    )

fun ExtendedIngredientRest.toExtendedIngredient() =
    ExtendedIngredient(
        amount = amount,
        consistency = consistency,
        image = image,
        name = name,
        original = original,
        unit = unit,
    )


//******************** REST to UI ******************************

fun FoodRecipes.toFoodRecipesRest() = FoodRecipesRest(results = recipes.map { it.toRecipeRest() })
fun Recipe.toRecipeRest() =
    RecipeRest(
        aggregateLikes = aggregateLikes,
        cheap = cheap,
        dairyFree = dairyFree,
        extendedIngredients = extendedIngredients?.map { it.toExtendedIngredientRest() },
        glutenFree = glutenFree,
        id = id,
        image = image,
        readyInMinutes = readyInMinutes,
        servings = servings,
        sourceName = sourceName,
        spoonacularSourceUrl = spoonacularSourceUrl,
        summary = summary,
        title = title,
        vegan = vegan,
        vegetarian = vegetarian,
        veryHealthy = veryHealthy,
        weightWatcherSmartPoints = weightWatcherSmartPoints
    )

fun ExtendedIngredient.toExtendedIngredientRest() =
    ExtendedIngredientRest(
        amount = amount,
        consistency = consistency,
        image = image,
        name = name,
        original = original,
        unit = unit,
    )

fun List<RecipesEntity>.toFoodRecipesRest(): FoodRecipesRest? =
    this.getOrNull(0)?.let {
        FoodRecipes(it.foodRecipes.recipes).toFoodRecipesRest()
    }
