package com.example.foodylearn.presentation.fragments.overview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.foodylearn.R
import kotlinx.android.synthetic.main.view_category.view.*

class CategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    LinearLayout(context, attrs) {

    private var view = inflate(context, R.layout.view_category, this)

     var active = false
        set(value) {
            field = value

            var color = R.color.green
            if (!value)
                color = R.color.categoryColor

           view.ivCheckCircle.setColorFilter(resources.getColor(color))
           view.tvCategoryTitle.setTextColor(resources.getColor(color))
        }

     var title = ""
        set(value) {
            field = value
            view.tvCategoryTitle.text = value
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