package com.example.foodylearn.data

import com.example.domain.models.ExtendedIngredientClean
import com.example.domain.models.FoodRecipesClean
import com.example.domain.models.RecipeClean
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.data.database.recipes.RecipesEntity
import com.example.foodylearn.data.models.ExtendedIngredient
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.data.models.Recipe


//******************** CLEAN to ANDROID ******************************
fun FoodRecipesClean.toFoodRecipes() =
    if (recipes.isNotEmpty())
        FoodRecipes(recipes = recipes.map { it.toRecipe() })
    else
        FoodRecipes(emptyList())
fun RecipeClean.toRecipe() =
    Recipe(
        aggregateLikes = aggregateLikes,
        cheap = cheap,
        dairyFree = dairyFree,
        extendedIngredients = extendedIngredientCleans?.map { it.toExtendedIngredient() },
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

fun ExtendedIngredientClean.toExtendedIngredient() =
    ExtendedIngredient(
        amount = amount,
        consistency = consistency,
        image = image,
        name = name,
        original = original,
        unit = unit,
    )


//******************** ANDROID to CLEAN ******************************

fun FoodRecipes.toFoodRecipesClean() = FoodRecipesClean(recipes = recipes.map { it.toRecipeClean() })
fun Recipe.toRecipeClean() =
    RecipeClean(
        aggregateLikes = aggregateLikes,
        cheap = cheap,
        dairyFree = dairyFree,
        extendedIngredientCleans = extendedIngredients?.map { it.toExtendedIngredientClean() },
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

fun ExtendedIngredient.toExtendedIngredientClean() =
    ExtendedIngredientClean(
        amount = amount,
        consistency = consistency,
        image = image,
        name = name,
        original = original,
        unit = unit,
    )

fun RecipesEntity.toFoodRecipesClean(): FoodRecipesClean = foodRecipes

fun List<FavoritesEntity>.toFoodRecipesClean() =
    FoodRecipesClean(map {
        it.recipe.toRecipeClean()
    })

