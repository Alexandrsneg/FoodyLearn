package com.example.foodylearn.presentation.fragments.overview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.foodylearn.R
import com.example.foodylearn.databinding.ViewCategoryBinding

class CategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    LinearLayout(context, attrs) {

    private var binding: ViewCategoryBinding =
        ViewCategoryBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        //        addView(binding.root)
    }


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
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CategoryView, 0, 0
        )
            try {
                active = a.getBoolean(R.styleable.CategoryView_cv_is_active, false)
                title = a.getString(R.styleable.CategoryView_cv_title) ?: ""
            } finally {
                a.recycle()
            }

    }
}