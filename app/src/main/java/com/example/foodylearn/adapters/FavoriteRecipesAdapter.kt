package com.example.foodylearn.adapters

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodylearn.R
import com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity
import com.example.foodylearn.databinding.FavoritesRowLayotBinding
import com.example.foodylearn.presentation.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.foodylearn.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorites_row_layot.view.*

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), android.view.ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoritesRowLayotBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritesRowLayotBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)

        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        /**
         * single Click Listener
         */
        holder.itemView.clFavoritesRecipesRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        currentRecipe.recipes
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long click
         */
        holder.itemView.clFavoritesRecipesRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }

    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.cardview_light_background, R.color.lightMediumGray)
            selectedRecipes.remove(currentRecipe)
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardActiveBackgroundColor, R.color.colorPrimary)
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
//        holder.itemView.clFavoritesRecipesRowLayout.setBackgroundColor(backgroundColor)
        holder.itemView.cvFavoriteCardContainer.strokeColor = strokeColor
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
//        favoriteRecipes = newFavoriteRecipes
//        notifyDataSetChanged()

        val recipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }


    override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatusBarColor(R.color.actionBarBackgroundColor)
        return true
    }

    override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: android.view.ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: android.view.ActionMode?) {
        myViewHolders.forEach{
            changeRecipeStyle(it, R.color.cardview_light_background, R.color.lightMediumGray)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

}