package com.ruurd.peruse.ui.callbacks

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ruurd.peruse.R
import kotlin.math.abs

abstract class SwipeCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    companion object SwipeBackgroundHelper {

        private const val THRESHOLD = 2.5
        private const val OFFSET_PX = 20
        private const val CIRCLE_ACCELERATION = 3f

        private var shadowColor: Int = 0
        private var topShadowHeight: Float = 0f
        private var bottomShadowHeight: Float = 0f
        private var sideShadowWidth: Float = 0f

        var leftShadow: Paint? = null
        var topShadow: Paint? = null
        var bottomShadow: Paint? = null

        private var initialized = false

        fun paintDrawCommandToStart(
            canvas: Canvas,
            viewItem: View,
            @DrawableRes iconResId: Int,
            dX: Float
        ) {
            val drawCommand = createDrawCommand(viewItem, dX, iconResId)
            paintDrawCommand(drawCommand, canvas, dX, viewItem)
        }

        private fun createDrawCommand(viewItem: View, dX: Float, iconResId: Int): DrawCommand {
            val context = viewItem.context
            var icon = ContextCompat.getDrawable(context, iconResId)
            icon = DrawableCompat.wrap(icon!!).mutate()
            icon.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, R.color.white),
                PorterDuff.Mode.SRC_IN
            )
            val backgroundColor = getBackgroundColor(dX, viewItem)

            return DrawCommand(icon, backgroundColor)
        }

        private fun getBackgroundColor(
            dX: Float,
            viewItem: View
        ): Int {
            return when (willActionBeTriggered(dX, viewItem.width)) {
                true -> ContextCompat.getColor(viewItem.context, R.color.colorDelete)
                false -> ContextCompat.getColor(viewItem.context, R.color.colorDeleteFaded)
            }
        }

        private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
            return abs(dX) >= viewWidth / THRESHOLD
        }

        private fun paintDrawCommand(
            drawCommand: DrawCommand,
            canvas: Canvas,
            dX: Float,
            viewItem: View
        ) {
            drawBackground(canvas, viewItem, dX, drawCommand)
            drawIcon(canvas, viewItem, dX, drawCommand.icon)
            drawShadow(canvas, viewItem, dX)
        }

        private fun drawBackground(
            canvas: Canvas,
            viewItem: View,
            dX: Float,
            drawCommand: DrawCommand
        ) {
            val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            backgroundPaint.color = drawCommand.backgroundColor
            val backgroundRectangle = getBackgroundRectangle(viewItem, dX)

            val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            circlePaint.color = ContextCompat.getColor(viewItem.context, R.color.colorDelete)

            val circleRadius =
                (abs(dX / viewItem.width) - 0.25f) * viewItem.width * CIRCLE_ACCELERATION
            canvas.clipRect(backgroundRectangle)
            canvas.drawColor(backgroundPaint.color)

            if (circleRadius > 0f) {
                val cy = backgroundRectangle.top + viewItem.height / 2
                val cx = backgroundRectangle.left + drawCommand.icon.intrinsicWidth / 2 + OFFSET_PX

                canvas.drawCircle(cx, cy, circleRadius, circlePaint)
            }
        }

        private fun getBackgroundRectangle(viewItem: View, dX: Float): RectF {
            return RectF(
                viewItem.right.toFloat() + dX,
                viewItem.top.toFloat(),
                viewItem.right.toFloat(),
                viewItem.bottom.toFloat()
            )
        }

        private fun drawIcon(canvas: Canvas, viewItem: View, dX: Float, icon: Drawable) {
            icon.bounds = getStartContainerRectangle(
                viewItem, icon.intrinsicWidth, calculateTopMargin(icon, viewItem), dX
            )
            icon.draw(canvas)
        }

        private fun calculateTopMargin(icon: Drawable, viewItem: View): Int {
            return (viewItem.height - icon.intrinsicHeight) / 2
        }

        private fun getStartContainerRectangle(
            viewItem: View,
            iconWidth: Int,
            topMargin: Int,
            dX: Float
        ): Rect {
            val leftBound = viewItem.right + dX.toInt() + OFFSET_PX
            val rightBound = viewItem.right + dX.toInt() + iconWidth + OFFSET_PX
            val topBound = viewItem.top + topMargin
            val bottomBound = viewItem.bottom - topMargin

            return Rect(leftBound, topBound, rightBound, bottomBound)
        }

        private fun drawShadow(canvas: Canvas, viewItem: View, dX: Float) {
            initialize(viewItem.context)

            val left = viewItem.left.toFloat()
            val right = viewItem.right.toFloat()
            val top = viewItem.top.toFloat()
            val bottom = viewItem.bottom.toFloat()

            topShadow?.let {
                val matrix = Matrix()
                matrix.setTranslate(0f, top)
                it.shader.setLocalMatrix(matrix)
                canvas.drawRect(left, top, right, top + topShadowHeight, it)
            }

            bottomShadow?.let {
                val matrix = Matrix()
                matrix.setTranslate(0f, bottom - bottomShadowHeight)
                it.shader.setLocalMatrix(matrix)
                canvas.drawRect(left, bottom - bottomShadowHeight, right, bottom, it)
            }

            leftShadow?.let {
                val shadowLeft = right + dX
                val matrix = Matrix()
                matrix.setTranslate(shadowLeft, 0f)
                it.shader.setLocalMatrix(matrix)
                canvas.drawRect(shadowLeft, top, shadowLeft + sideShadowWidth, bottom, it)
            }
        }

        private fun initialize(context: Context) {
            if (initialized) {
                return
            }

            shadowColor = ContextCompat.getColor(context, R.color.shadow)
            topShadowHeight = context.resources.getDimension(R.dimen.spacing_shadow)
            bottomShadowHeight = topShadowHeight / 2.5f
            sideShadowWidth = topShadowHeight * 3f / 4f

            topShadow = Paint().apply {
                shader = LinearGradient(0f, 0f, 0f, topShadowHeight, shadowColor, 0, TileMode.CLAMP)
            }
            bottomShadow = Paint().apply {
                shader =
                    LinearGradient(0f, 0f, 0f, bottomShadowHeight, shadowColor, 0, TileMode.CLAMP)
            }
            leftShadow = Paint().apply {
                shader = LinearGradient(0f, 0f, sideShadowWidth, 0f, shadowColor, 0, TileMode.CLAMP)
            }

            initialized = true
        }

        private class DrawCommand internal constructor(
            internal val icon: Drawable,
            internal val backgroundColor: Int
        )
    }
}