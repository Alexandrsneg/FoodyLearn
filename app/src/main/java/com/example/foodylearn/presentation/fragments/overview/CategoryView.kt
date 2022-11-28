package com.example.foodylearn.presentation.fragments.overview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.foodylearn.R
import com.example.foodylearn.databinding.ViewCategoryBinding

class CategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : LinearLayout(context, attrs) {

    private var binding: ViewCategoryBinding =
        ViewCategoryBinding.inflate(LayoutInflater.from(context), this, true)

    var active = false
        set(value) {
            field = value

            var color = R.color.green
            if (!value)
                color = R.color.categoryColor

            binding.ivCheckCircle.setColorFilter(resources.getColor(color))
            binding.tvCategoryTitle.setTextColor(resources.getColor(color))
        }

    var title = ""
        set(value) {
            field = value
            binding.tvCategoryTitle.text = value
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.CategoryView, defStyleAttrs) {
            active = getBoolean(R.styleable.CategoryView_cv_is_active, false)
            title = getString(R.styleable.CategoryView_cv_title) ?: ""
        }
    }
}