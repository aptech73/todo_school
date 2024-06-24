package dev.aptech.todoapp.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.aptech.todoapp.R

abstract class SwipeToDeleteCallback(context: Context)
    : ItemTouchHelper.SimpleCallback(0, (ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete)
    private val deleteIconIntrinsicWidth = deleteIcon?.intrinsicWidth
    private val deleteIconIntrinsicHeight = deleteIcon?.intrinsicHeight

    private val archiveIcon = ContextCompat.getDrawable(context, R.drawable.ic_check_24)
    private val archiveIconIntrinsicWidth = archiveIcon?.intrinsicWidth
    private val archiveIconIntrinsicHeight = archiveIcon?.intrinsicHeight

    private val background = ColorDrawable()
    private val leftBackgroundColor = ContextCompat.getColor(context, R.color.color_red)
    private val rightBackgroundColor = ContextCompat.getColor(context, R.color.color_green)
    private val clearPaint = Paint().apply { color = Color.TRANSPARENT}

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        val isCanceled = dX == 0f && !isCurrentlyActive
        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        val isLeftDirection = dX < 0
        if (isLeftDirection) {
            background.color = leftBackgroundColor
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        } else {
            background.color = rightBackgroundColor
            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        }
        background.draw(c)

        val itemHeight = itemView.bottom - itemView.top
        if (deleteIcon != null
            && deleteIconIntrinsicWidth != null
            && deleteIconIntrinsicHeight != null
            && archiveIcon != null
            && archiveIconIntrinsicWidth != null
            && archiveIconIntrinsicHeight != null) {

            if (isLeftDirection) {
                val deleteIconTop = itemView.top + (itemHeight - deleteIconIntrinsicHeight) / 2
                val deleteIconMargin = (itemHeight - deleteIconIntrinsicHeight) / 2
                val deleteIconLeft = itemView.right - deleteIconMargin - deleteIconIntrinsicWidth
                val deleteIconRight = itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + deleteIconIntrinsicHeight

                deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                deleteIcon.draw(c)
            } else {
                val archiveIconTop = itemView.top + (itemHeight - archiveIconIntrinsicHeight) / 2
                val archiveIconMargin = (itemHeight - archiveIconIntrinsicHeight) / 2
                val archiveIconLeft = itemView.left + archiveIconMargin
                val archiveIconRight = itemView.left + (archiveIconMargin + archiveIconIntrinsicWidth)
                val archiveIconBottom = archiveIconTop + archiveIconIntrinsicHeight

                archiveIcon.setBounds(archiveIconLeft, archiveIconTop, archiveIconRight, archiveIconBottom)
                archiveIcon.draw(c)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}