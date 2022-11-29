package com.example.foodylearn.presentation.adapters

import android.view.*
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
import com.example.foodylearn.presentation.viewmodels.MainViewModel

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), android.view.ActionMode.Callback {

    private var favoriteRecipes = arrayListOf<com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity>()
    private var mActionMode: ActionMode? = null

    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    class MyViewHolder(val binding: FavoritesRowLayotBinding) :
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
        holder.binding.clFavoritesRecipesRowLayout.setOnClickListener {
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
        holder.binding.clFavoritesRecipesRowLayout.setOnLongClickListener {
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

    private fun applySelection(holder: MyViewHolder, currentRecipe: com.example.foodylearn.data.database.favorites.favorites.FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardview_light_background, R.color.lightMediumGray)
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardActiveBackgroundColor, R.color.colorPrimary)
        }
        applyActionModeTitle()
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
//        holder.itemView.clFavoritesRecipesRowLayout.setBackgroundColor(backgroundColor)
        holder.binding.cvFavoriteCardContainer.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> mActionMode?.finish()
            1 -> mActionMode?.title = "${selectedRecipes.size} item selected"
            else -> mActionMode?.title = "${selectedRecipes.size} items selected"
        }
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes as ArrayList<FavoritesEntity>
        diffUtilResult.dispatchUpdatesTo(this)
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = mode
        applyStatusBarColor(R.color.actionBarBackgroundColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        selectedRecipes.forEach {
            selectedRecipes.remove(it)
            mainViewModel.deleteFavorite(it)
        }
        setData(selectedRecipes)
        mActionMode?.finish()
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach {
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

    fun closeActionMenu(){
        mActionMode?.finish()
    }
}