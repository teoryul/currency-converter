package com.teoryul.currencyconverter.adapter.helper

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.teoryul.currencyconverter.App
import com.teoryul.currencyconverter.R
import com.teoryul.currencyconverter.ui.fragment.savedconfigurations.SavedConfigurationsVM

class SwipeToDeleteSavedConfigurations(private val viewModel: SavedConfigurationsVM) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val icon = ContextCompat.getDrawable(App.instance, R.drawable.ic_delete)
    private val background = ColorDrawable(ContextCompat.getColor(App.instance, R.color.colorAccent))

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        targetViewHolder: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
            viewModel.onSwipeDelete(viewHolder.adapterPosition)
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && icon != null) {
            val itemView = viewHolder.itemView

            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight

            when {
                // When swiping the view to the right
                dX > 0 -> {
                    val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                    val iconRight = itemView.left + iconMargin
                    icon.setBounds(iconRight, iconTop, iconLeft, iconBottom)
                    background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                }

                // When swiping the view to the left
                dX < 0 -> {
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                }

                // When the view isn't swiped
                else -> background.setBounds(0, 0, 0, 0)
            }

            background.draw(canvas)
            icon.draw(canvas)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}