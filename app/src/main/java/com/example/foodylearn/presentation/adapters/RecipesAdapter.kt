package com.example.foodylearn.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodylearn.databinding.RecipiesRowLayoutBinding
import com.example.foodylearn.data.models.FoodRecipes
import com.example.foodylearn.data.models.Recipe
import com.example.foodylearn.util.RecipesDiffUtil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Recipe>()

    class MyViewHolder(private val binding: RecipiesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe){
            binding.result = recipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipiesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipes){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.recipes)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.recipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}