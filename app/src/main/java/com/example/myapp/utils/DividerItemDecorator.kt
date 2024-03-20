package com.example.myapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R

class DividerItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_divider)

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = parent.paddingLeft
        val dividerRight = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = mDivider?.intrinsicHeight?.plus(dividerTop)
            dividerBottom?.let { dividerBottom -> mDivider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom) }
            mDivider?.draw(canvas)
        }
    }

}