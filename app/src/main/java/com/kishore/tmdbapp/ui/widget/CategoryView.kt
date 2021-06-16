package com.kishore.tmdbapp.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kishore.tmdbapp.R
import com.kishore.tmdbapp.databinding.ViewCategoryBinding

class CategoryView : ConstraintLayout {
    private val mTAG = CategoryView::class.java.simpleName
    private lateinit var binding: ViewCategoryBinding
    private var viewMoreListener: OnClickListener? = null

    /**
     * Constructor Called via programming in class
     * Eg: val customView = CategoryView(context)
     * */
    constructor(context: Context) : super(context) {
        initView(context = context)
    }

    /***
     * Constructor called via XML Files
     */
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context = context, attributeSet = attributeSet)
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initView(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) {
        binding = ViewCategoryBinding.inflate(LayoutInflater.from(context), this, true)
        try {
            binding.btnViewMore.setOnClickListener(viewMoreListener)
        } catch (e: NullPointerException) {
            throw Exception("View More SetOnClickListener is not initialized.")
        }
        attributeSet?.let { attr ->
            context.theme.obtainStyledAttributes(attr, R.styleable.CategoryView, defStyleAttr, 0)
                .apply {
                    try {
                        val name = getString(R.styleable.CategoryView_categoryName)
                        binding.txtViewCategoryTitle.text = name
                        binding.btnViewMore.tag = name
                        binding.txtViewCategoryTitle.textSize =
                            getDimensionPixelOffset(
                                R.styleable.CategoryView_categoryTextSize,
                                22).toFloat()
                    } catch (e: Exception) {
                        Log.e(mTAG, e.localizedMessage)
                        recycle()
                    }
                }
        }
    }

    /***
     * Set OnClickListener to View More TextView
     */
    fun setOnViewMoreClickListener(listener: OnClickListener) {
        viewMoreListener = listener
        binding.btnViewMore.setOnClickListener(viewMoreListener)
    }

    /***
     * Set LayoutManager and Adapter to RecyclerView
     */
    fun setCategoryAdapter(
        layoutManager: RecyclerView.LayoutManager,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        binding.rvCategoryList.layoutManager = layoutManager
        binding.rvCategoryList.adapter = adapter
    }
}