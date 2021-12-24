package com.example.foodylearn.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodylearn.databinding.IngredientsRowLayoutBinding
import com.example.foodylearn.models.ExtendedIngredient
import com.example.foodylearn.util.RecipesDiffUtil

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredients = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient){
            binding.ingredient = ingredient
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = ingredients[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setData(newData: List<ExtendedIngredient>){
//        newData?.let {
//            ingredients = newData
//            notifyDataSetChanged()
//        }

        val recipesDiffUtil = RecipesDiffUtil(ingredients, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredients = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}